package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityCompareAcrivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth

class Compare_Acrivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompareAcrivityBinding
    private val viewModel = MainViewMod()
    private var navigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompareAcrivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationView = binding.navigationBar
        navigationView?.setSelectedItemId(R.id.home)

        navigationView?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@Compare_Acrivity, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.compare -> true
                R.id.favorits -> {
                    startActivity(Intent(this@Compare_Acrivity, Favorities_Activity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.profile -> {
                    val intent = if (FirebaseAuth.getInstance().currentUser == null) {
                        Intent(this@Compare_Acrivity, Login_Activity::class.java)
                    } else {
                        Intent(this@Compare_Acrivity, Account_Activity::class.java)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        })

        initCategoryCompare()
    }

    private fun initCategoryCompare() {
        binding.progressBarCompareCategory.visibility = View.VISIBLE
        viewModel.category.observe(this, Observer { categories ->
            binding.recyyclerViewCompareCategory.layoutManager = LinearLayoutManager(
                this@Compare_Acrivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyyclerViewCompareCategory.adapter = AdaptCategoryCompare(categories.toMutableList())
            binding.progressBarCompareCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }
}