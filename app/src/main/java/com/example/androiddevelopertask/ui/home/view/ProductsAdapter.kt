package com.example.androiddevelopertask.ui.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androiddevelopertask.databinding.ProductItemBinding
import com.example.androiddevelopertask.model.Product

class ProductsAdapter(private val clickListener: ProductClickListener) : ListAdapter<Product, ProductsAdapter.MyViewHolder>(
    ProductDiffCallback()
) {

    class MyViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, clickListener: ProductClickListener) {
            binding.apply {

                Glide.with(root.context).load(product.thumbnail).into(ivProduct)

                if (product.stock > 50 ) {
                    tvTitle.text = "Price: ${product.price}$"
                    tvPrice.text = product.title
                } else {
                    tvTitle.text = product.title
                    tvPrice.text = "Price: ${product.price}$"
                }

                tvDesc.text = product.description
                layout.setOnClickListener {
                    clickListener.onClick(product)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    class ProductClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}