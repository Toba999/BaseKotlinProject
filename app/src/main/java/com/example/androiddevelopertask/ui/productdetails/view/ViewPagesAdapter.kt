package com.example.androiddevelopertask.ui.productdetails.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androiddevelopertask.databinding.ItemProductImageBinding

class ViewPagesAdapter(private val images: List<String>) : RecyclerView.Adapter<ViewPagesAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String) {
            binding.apply {
                Glide.with(root.context).load(image).into(ivProduct)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductImageBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(images[position])
    }
}