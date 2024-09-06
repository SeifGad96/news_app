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
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.training.whatsthenews.databinding.ArticleListItemBinding

class NewsAdapter(val fragment: Fragment, val articles: ArrayList<Article>) :
    Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val binding: ArticleListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.articleText.text = articles[position].title
        Glide
            .with(holder.binding.articleIv.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleIv)

        val url = articles[position].uniformResourceLocator

        val db = Firebase.firestore
        // adding favorites news into fire_store database without duplicating and the name of the document is the title of news
        holder.binding.favoriteIv.setOnClickListener {
            val news = Article(articles[position].title, url, articles[position].urlToImage)

            db
                .collection("Favorites News").document(articles[position].title)
                .set(news)
                .addOnSuccessListener {
                    Toast.makeText(
                        fragment.requireActivity(),
                        "added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("test", "$news")
                }
                .addOnFailureListener {
                    Log.d("test", "${it.message}")
                }
        }

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


    }
}