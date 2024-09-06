package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewMod():ViewModel(){
    private val firebaseDatabase=FirebaseDatabase.getInstance()
    private  val _category=MutableLiveData<MutableList<Category>>()

    val category:LiveData<MutableList<Category>> = _category

    fun loadCategory(){
        val Ref = firebaseDatabase.getReference("Category")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<Category>()
                for (childSnapshot in snapshot.children){
                    val list = childSnapshot.getValue(Category::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _category.value=lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}