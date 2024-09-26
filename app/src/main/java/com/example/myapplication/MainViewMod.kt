package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class MainViewMod : ViewModel() {
    private val firestoreDatabase = FirebaseFirestore.getInstance()

    private val _category = MutableLiveData<MutableList<Category>>()
    private val _recommended = MutableLiveData<MutableList<ItemsModel>>()

    val category: LiveData<MutableList<Category>> = _category
    val recommended: LiveData<MutableList<ItemsModel>> = _recommended

    fun loadFiltered(id: String) {
        val ref = firestoreDatabase.collection("Items")
        ref.whereEqualTo("categoryId", id)
            .get()
            .addOnSuccessListener { documents ->
                val lists = mutableListOf<ItemsModel>()
                for (document in documents) {
                    val item = document.toObject(ItemsModel::class.java)
                    lists.add(item)
                }
                _recommended.value = lists
            }
            .addOnFailureListener {
            }
    }

    fun loadRecommended() {
        val ref = firestoreDatabase.collection("Items")
        ref.whereEqualTo("showRecommended", true)
            .get()
            .addOnSuccessListener { documents ->
                val lists = mutableListOf<ItemsModel>()
                for (document in documents) {
                    val item = document.toObject(ItemsModel::class.java)
                   item.iditeam=document.id
                    lists.add(item)
                }
                _recommended.value = lists
            }
            .addOnFailureListener {

            }
    }

    fun loadCategory() {
        val ref = firestoreDatabase.collection("Category")
        ref.get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                val lists = mutableListOf<Category>()
                for (document in documents) {
                    val category = document.toObject(Category::class.java)
                    lists.add(category)
                }
                _category.value = lists
            }
            .addOnFailureListener {

            }
    }
}