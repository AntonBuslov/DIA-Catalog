package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewMod
    private lateinit var adapter: ItemAdapter
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
        setupSearchView()

        intent.getStringExtra("query")?.let {
            if (it.isNotEmpty()) {
                binding.searchView1.setQuery(it, false)
                viewModel.filterItemsByTitle(it)
            }
        }

        adapter.setOnItemClickListener { item ->
            startActivity(Intent(this, DetailAktivity::class.java).apply {
                putExtra("object", item)
            })
            binding.backBtn.setOnClickListener{ finish()}
        }
        binding.backBtn.setOnClickListener{ finish()}
        viewModel.allItems.observe(this) { items ->
            adapter.setItems(items)
        }

        viewModel.loadAllItems()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewMod::class.java)
    }

    private fun setupRecyclerView() {
        adapter = ItemAdapter()
        binding.recyclerViewuItemsearch.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewuItemsearch.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView1.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.takeIf { it.isNotEmpty() }?.let {
                    viewModel.filterItemsByTitle(it)
                    binding.searchView1.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        viewModel.loadAllItems()
                    } else {
                        viewModel.filterItemsByTitle(it)
                    }
                }
                return true
            }
        })
    }
}