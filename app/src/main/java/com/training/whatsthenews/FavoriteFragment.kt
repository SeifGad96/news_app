package com.training.whatsthenews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.training.whatsthenews.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.root.findViewById<Toolbar>(R.id.app_tool_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        // Disable default title
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        val title = toolbar.findViewById<TextView>(R.id.toolbar_title)
        val backBtn = toolbar.findViewById<ImageView>(R.id.back_btn)
        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
        title.text = "Favorites"
        // adding menu option and favorite heart
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items
                menuInflater.inflate(R.menu.favorite_menu, menu)
            }

            // Handle the menu selection
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_favorites -> {
                        // write a code to delete the data from the firestore and database
                        viewModel.deleteAllFavorites()
                        viewModel.deleteFromDB(viewLifecycleOwner)
                        // Clear the RecyclerView adapter data
                        (binding.favoriteRv.adapter as FavoriteNewsAdapter).apply {
                            favoriteNews = emptyList()
                            notifyDataSetChanged()
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Observe favorites from Firebase
        viewModel.favoriteNews.observe(viewLifecycleOwner) { favoriteArticles ->
            // Update UI with the favorite articles from Firebase
            val adapter = FavoriteNewsAdapter(this@FavoriteFragment, favoriteArticles)
            binding.favoriteRv.adapter = adapter
        }

        // Observe offline favorites from Room database
        viewModel.getOfflineFavoriteArticles().observe(viewLifecycleOwner) { favoriteArticles ->
            // Update UI with the favorite articles from Room database
            val adapter =
                FavoriteNewsAdapter(this@FavoriteFragment, mapToFavoriteNews(favoriteArticles))
            binding.favoriteRv.adapter = adapter
        }

        viewModel.loadFavoriteArticles()


        }

    // convert the data from List<FavoriteNewsEntity> to List<FavoriteNews> to be sent to the adapter which takes List<FavoriteNews>
    private fun mapToFavoriteNews(entityList: List<FavoriteNewsEntity>): List<FavoriteNews> {
        return entityList.map { entity ->
            FavoriteNews(
                title = entity.title,
                uniformResourceLocator = entity.uniformResourceLocator,
                urlToImage = entity.urlToImage
            )
        }
    }
}



