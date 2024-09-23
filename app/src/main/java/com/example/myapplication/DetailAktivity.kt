package com.example.myapplication


import android.os.Bundle
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityDetailBinding


class DetailAktivity : BaseActivity() {
    private  lateinit var binding: ActivityDetailBinding
    private lateinit var  item:ItemsModel
    private lateinit var compareButtonHandler: CompareButtonHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compareButtonHandler = CompareButtonHandler(this, binding.compareBtn)
        getBundle()
        compareButtonHandler.setInitialState(item)

        binding.compareBtn.setOnClickListener {
            compareButtonHandler.toggleCompare(item)
        }



        getBundle()
        initList()
        initSiteList()
        initCharacteristicsList()
    }

    private fun initCharacteristicsList() {
            binding.characteristicList.layoutManager = LinearLayoutManager(this)
            binding.characteristicList.adapter = CharacteristicsAdapter(item.characteristics)
        
    }

    private fun initSiteList() {
        val siteList = ArrayList<String>()
        for (siteUrl in item.siteUrl) {
            siteList.add(siteUrl)
        }
        binding.saitList.adapter = SiteAdapter(siteList)
        binding.saitList.layoutManager = LinearLayoutManager(this)
    }

    private fun initList() {
        val modelList = arrayListOf<String>()
        for (models in item.model) {
            modelList.add(models)
        }
        binding.modelList.adapter=SelectModelAdapter(modelList)
        binding.modelList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val picList=ArrayList<String>()
        for(imageUrl in item.picUrl){
            picList.add(imageUrl)
        }
        Glide.with(this)
            .load(picList[0])
            .into(binding.img)

        binding.picList.adapter=PicAdapter(picList){selectedImageUrl->
            Glide.with(this)
                .load(selectedImageUrl)
                .into(binding.img)
        }

    binding.picList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }

    private  fun getBundle(){
        item = intent.getParcelableExtra("object")!!

    binding.titleTxt.text=item.title
    binding.descriptionTxt.text=item.description
    binding.priceTxt.text="$"+item.price
    binding.ratingTxt.text="${item.rating}Rating"
binding.backBtn.setOnClickListener{ finish()}
}
}