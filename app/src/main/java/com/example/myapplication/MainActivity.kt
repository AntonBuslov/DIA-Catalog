package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewMod()
    var navigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationView = binding.navigationBar
        navigationView?.setSelectedItemId(R.id.home)

        navigationView?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            if (item.itemId == R.id.home) {
                return@OnItemSelectedListener true
            }
            if (item.itemId == R.id.compare) {
                startActivity(Intent(this@MainActivity, Compare_Acrivity::class.java))
                overridePendingTransition(0, 0)
                return@OnItemSelectedListener true
            }
            if (item.itemId == R.id.favorits) {
                startActivity(Intent(this@MainActivity, Favorities_Activity::class.java))
                overridePendingTransition(0, 0)
                return@OnItemSelectedListener true
            }
            if (item.itemId == R.id.profile) {
                if (FirebaseAuth.getInstance().currentUser == null) {
                    startActivity(Intent(this@MainActivity, Login_Activity::class.java))
                    overridePendingTransition(0, 0)
                } else {
                    startActivity(Intent(this@MainActivity, Account_Activity::class.java))
                    overridePendingTransition(0, 0)
                }

                return@OnItemSelectedListener true
            }
            false
        })
        initCategory()
        initRecommeded()
    }

    private fun initRecommeded() {
        binding.progressBarCategory.visibility=View.VISIBLE
        viewModel.recommended.observe(this,Observer{
            binding.viewRecommendation.layoutManager=GridLayoutManager(this@MainActivity,2)
            binding.viewRecommendation.adapter=RecommendedAdapt(it)
            binding.progressBarRecommend.visibility=View.GONE
        })
        viewModel.loadRecommended()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility=View.VISIBLE
        viewModel.category.observe(this,Observer{
            binding.recyclerViewCategory.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerViewCategory.adapter=AdaptCategory(it)
            binding.progressBarCategory.visibility=View.GONE
        })
        viewModel.loadCategory()
    }
}