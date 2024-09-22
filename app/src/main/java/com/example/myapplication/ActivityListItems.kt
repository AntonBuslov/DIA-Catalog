package com.example.myapplication

import android.os.Bundle
import android.view.View

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityListItemsBinding


class ActivityListItems : BaseActivity() {
    private lateinit var binding: ActivityListItemsBinding
    private val viewModel = MainViewMod()
    private var id: String = ""
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getBundle()
        initList()
    }

    private fun initList() {
        binding.apply {
            progressBarlist.visibility = View.VISIBLE
            viewModel.recommended.observe(this@ActivityListItems, Observer {
                viewList.layoutManager = GridLayoutManager(this@ActivityListItems, 2)
                viewList.adapter = ListItemAdapt(it)
                progressBarlist.visibility = View.GONE

            })
            viewModel.loadFiltered(id)
        }
    }

    private fun getBundle() {
        id=intent.getStringExtra("id")!!
        title=intent.getStringExtra("title")!!

        binding.categoryTxt.text = title
        binding.backBtn.setOnClickListener{ finish()}
    }
}
