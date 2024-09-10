package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ViewRecommendedBinding

class RecommendedAdapt(val items:MutableList<ItemsModel>):RecyclerView.Adapter<RecommendedAdapt.Viewholder>() {
    class Viewholder(val binding: ViewRecommendedBinding)  :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedAdapt.Viewholder {
       val binding=ViewRecommendedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedAdapt.Viewholder, position: Int) {
        val item=items[position]
        with(holder.binding){
            titleTxt.text=item.title
            priceTxt.text="$${item.price}"
            ratingTxt.text=item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)
            root.setOnClickListener{

            }
        }
    }

    override fun getItemCount(): Int = items.size


}