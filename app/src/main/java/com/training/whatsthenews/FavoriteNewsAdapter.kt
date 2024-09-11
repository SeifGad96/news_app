package com.training.whatsthenews

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.training.whatsthenews.databinding.FavoriteListItemBinding

class FavoriteNewsAdapter(val fragment :Fragment,var favoriteNews:List<FavoriteNews>) : Adapter<FavoriteNewsAdapter.FavoriteNewsViewHolder>() {
    class FavoriteNewsViewHolder(val binding : FavoriteListItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNewsViewHolder {
         val binding =
            FavoriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteNewsViewHolder(binding)
    }

    override fun getItemCount() = favoriteNews.size

    override fun onBindViewHolder(holder: FavoriteNewsViewHolder, position: Int) {
        Log.d("errors","${favoriteNews.size}")
        holder.binding.articleText.text = favoriteNews[position].title
        Glide
            .with(holder.binding.articleIv.context)
            .load(favoriteNews[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleIv)

        val url = favoriteNews[position].uniformResourceLocator

        holder.binding.articleContainer.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            fragment.startActivity(i)
        }

        holder.binding.shareFab.setOnClickListener {
            ShareCompat
                .IntentBuilder(fragment.requireContext())
                .setType("text/plain")
                .setChooserTitle("Share with:")
                .setText(url)
                .startChooser()
        }
        //val url = articles[position].uniformResourceLocator

        holder.binding.savedFavFab.setOnClickListener {
            //write a code to delete the data from the firestore and database
            // Clear the RecyclerView adapter data
            val newsTitle = favoriteNews[position].title
            val db = Firebase.firestore
            db.collection("Favorites News").document(newsTitle)
                .delete()
                .addOnSuccessListener {
                    // Document successfully deleted!
                    // Optionally, remove the item from your local list and update the adapter
                    favoriteNews = favoriteNews.toMutableList().also { it.removeAt(position) }
                    notifyItemRemoved(position)
                    Toast.makeText(fragment.requireActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e -> Log.w("TAG", "Error deleting document", e) }

        }

    }


}