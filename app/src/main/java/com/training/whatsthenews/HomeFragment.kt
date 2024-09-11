package com.training.whatsthenews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.training.whatsthenews.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    //https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=b8cf8e3608cc463981aa21c1f53a317d usa
    //https://newsapi.org/v2/top-headlines?country=eg&category=general&apiKey=b8cf8e3608cc463981aa21c1f53a317d egypt
    //https://newsapi.org/v2/top-headlines?country=ed&category=general&apiKey=b8cf8e3608cc463981aa21c1f53a317d jermany
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            requireActivity().finish()
        }

        // Set up Toolbar as ActionBar
        val toolbar = binding.root.findViewById<Toolbar>(R.id.app_tool_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        // Disable default title
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        val title = toolbar.findViewById<TextView>(R.id.toolbar_title)
        val backBtn = toolbar.findViewById<ImageView>(R.id.back_btn)
        backBtn.isVisible = false
        title.text = "News/Categories"

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logout -> {
                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                        true
                    }

                    R.id.favorites_menu_list -> {
                        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                        true
                    }

                    R.id.favorites_action_bar -> {
                        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                        true
                    }

                    R.id.settings -> {
                        findNavController().navigate(R.id.action_homeFragment_to_setting)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        showCategories(loadCategories())
        loadNews()

        binding.swipeRefresh.setOnRefreshListener {
            loadNews()
        }
    }

    private fun loadNews() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val c = retrofit.create(NewsCallable::class.java)
        val viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        val sharedPref = requireContext().getSharedPreferences("newsAppPref", Context.MODE_PRIVATE)
        val savedCountry = sharedPref.getString("selectedCountry", "us")


        viewModel.categoryImageView.observe(viewLifecycleOwner) { it ->
            when (savedCountry) {
                "eg" ->
                    when (it) {
                        R.drawable.tech -> c.getTechnoNewsegypt().enqueue(newsCallback("Technology News:"))
                        R.drawable.health -> c.getHealthNewsegypt().enqueue(newsCallback("Health News"))
                        R.drawable.sports -> c.getSportNewsegypt().enqueue(newsCallback("Sports News"))
                        R.drawable.business -> c.getBusinessNewsegypt().enqueue(newsCallback("Business News"))
                    }
                "us" ->
                    when (it) {
                        R.drawable.tech -> c.getTechnoNewsus().enqueue(newsCallback("Technology News:"))
                        R.drawable.health -> c.getHealthNewsus().enqueue(newsCallback("Health News"))
                        R.drawable.sports -> c.getSportNewsus().enqueue(newsCallback("Sports News"))
                        R.drawable.business -> c.getBusinessNewsus().enqueue(newsCallback("Business News"))
                    }
                "fr" ->
                    when (it) {
                        R.drawable.tech -> c.getTechnoNewsfrance().enqueue(newsCallback("Technology News:"))
                        R.drawable.health -> c.getHealthNewsfrance().enqueue(newsCallback("Health News"))
                        R.drawable.sports -> c.getSportNewsfrance().enqueue(newsCallback("Sports News"))
                        R.drawable.business -> c.getBusinessNewsfrance().enqueue(newsCallback("Business News"))
                    }
                "de" ->
                    when (it) {
                        R.drawable.tech -> c.getTechnoNewsjermany().enqueue(newsCallback("Technology News:"))
                        R.drawable.health -> c.getHealthNewsjermany().enqueue(newsCallback("Health News"))
                        R.drawable.sports -> c.getSportNewsjermany().enqueue(newsCallback("Sports News"))
                        R.drawable.business -> c.getBusinessNewsjermany().enqueue(newsCallback("Business News"))
                    }
                "gb" ->
                    when (it) {
                        R.drawable.tech -> c.getTechnoNewsengland().enqueue(newsCallback("Technology News:"))
                        R.drawable.health -> c.getHealthNewsengland().enqueue(newsCallback("Health News"))
                        R.drawable.sports -> c.getSportNewsengland().enqueue(newsCallback("Sports News"))
                        R.drawable.business -> c.getBusinessNewsengland().enqueue(newsCallback("Business News"))
                    }
                else->c.getTechnoNewsengland().enqueue(newsCallback("Technology News:"))
            }
        }

    }

    private fun newsCallback(newsType: String): Callback<News> {
        return object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                val articles = news?.articles!!
                articles.removeAll { it.title == "[Removed]" }
                showNews(articles)
                binding.textView1.text = newsType
                binding.swipeRefresh.isRefreshing = false
                binding.progressBar.isVisible = false
                Log.d("trace", "Articles: $articles")
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace", "Error: ${t.message}")
            }
        }
    }


    private fun showNews(articles: ArrayList<Article>) {
        val newsAdapter = NewsAdapter(this, articles)
        binding.newsRv.adapter = newsAdapter
    }

    private fun showCategories(categories: List<Category>) {
        val categoriesAdapter = CategoriesAdapter(this, categories)
        binding.categoryRv.adapter = categoriesAdapter
    }


    private fun loadCategories(): List<Category> {
        // Load categories and news
        val categories = arrayOf(
            R.drawable.tech,
            R.drawable.health,
            R.drawable.sports,
            R.drawable.business
        )
        val categoryNames = arrayOf(
            "Technology",
            "Health",
            "Sports",
            "Business"
        )
        val categoryList = mutableListOf<Category>()
        for (i in categories.indices) {
            categoryList.add(Category(categories[i], categoryNames[i]))
        }
        return categoryList
    }

}