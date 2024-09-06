package com.training.whatsthenews

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoriteNews = MutableLiveData<List<FavoriteNews>>()
    val favoriteNews: LiveData<List<FavoriteNews>>
        get() = _favoriteNews

    private val favoriteNewsDao: FavoriteNewsDao = AppDatabase.getInstance(application).dao

    fun loadFavoriteArticles() {
        val db = Firebase.firestore
        db.collection("Favorites News")
            .get()
            .addOnSuccessListener { result ->
                val favoriteNewsList = result.toObjects(FavoriteNews::class.java)
                _favoriteNews.value = favoriteNewsList

                // Insert into Room database
                viewModelScope.launch {
                    val favoriteNewsEntities = favoriteNewsList.map {
                        FavoriteNewsEntity(
                            title = it.title,
                            uniformResourceLocator = it.uniformResourceLocator,
                            urlToImage = it.urlToImage
                        )
                    }
                    favoriteNewsDao.insertFavoriteNews(favoriteNewsEntities)
                }
                // Log for debugging purposes (optional)
                Log.d("favorite", "Favorite news loaded: ${_favoriteNews.value}")
            }
            .addOnFailureListener { exception ->
                // Handle any errors that might occur while fetching data
                Log.e("favorite", "Error fetching favorite news", exception)
            }
    }

    fun getOfflineFavoriteArticles(): LiveData<List<FavoriteNewsEntity>> {
        return favoriteNewsDao.getFavoriteNews()
    }
    val db = Firebase.firestore
    fun deleteAllFavorites() {
        viewModelScope.launch {


            try {
                // Fetch all documents from Firestore
                val result = db.collection("Favorites News").get().await()
                val batch = db.batch()

                // Add each document to the batch for deletion
                for (document in result.documents) {
                    batch.delete(document.reference)
                }

                // Commit the batch
                batch.commit().addOnSuccessListener {
                    Log.d("favorite", "All favorites deleted from Firestore")
                }.addOnFailureListener { exception ->
                    Log.e("favorite", "Error deleting favorites from Firestore", exception)
                }

            } catch (e: Exception) {
                Log.e("favorite", "Error deleting favorites", e)
            }
        }

    }


    fun deleteFromDB(lifecycleOwner: LifecycleOwner) {

        favoriteNewsDao.getFavoriteNews().observe(lifecycleOwner) { favoriteNewsList ->
            // Delete from Room
            viewModelScope.launch {
                favoriteNewsDao.deleteFavoriteNews(favoriteNewsList)
                Log.d("favorite", "All favorites deleted from Room")
            }
        }

    }


}
