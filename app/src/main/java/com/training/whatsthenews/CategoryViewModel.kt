package com.training.whatsthenews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    /*
    I used view model to get the listener from clicking on cardview
    & live data to update the news
    * */
    private val _categoryImageView = MutableLiveData<Int>(R.drawable.tech)

    val categoryImageView: LiveData<Int>
        get() = _categoryImageView

    fun updateCategoryImage(newImageRes: Int) {
        _categoryImageView.value = newImageRes
    }
}
