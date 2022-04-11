package com.example.myshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.common.ProgressCircleGlide
import com.example.myshop.databinding.ItemsInCartBinding
import com.example.myshop.domain.models.Products
import com.example.myshop.domain.models.ProductsInCart

class ProductsInCartAdapter(private var itemsQuantity:  Int, private val listProductsInCart: MutableList<ProductsInCart>): RecyclerView.Adapter<ProductsInCartAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(var binding: ItemsInCartBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemsInCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listProductsInCart.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = listProductsInCart[position]
              holder.itemView.apply {
            Glide.with(this).load(products.image).placeholder(ProgressCircleGlide.progressBar(context)).
            into(holder.binding.ivProduct)
        }
        holder.binding.tvPrice.text = "${products.price}  ${products.currency}"
        holder.binding.tvTitle.text = products.title
        itemsQuantity = products.quantity
        holder.binding.tvQuantity.text = itemsQuantity.toString()
        holder.binding.ibPlus.setOnClickListener {
            onItemClickListenerPlus.let {
                it(products)
            }
        }
        holder.binding.ibMinus.setOnClickListener {
            onItemClickListenerMinus.let {
                it(products)
            }
        }
        holder.binding.ivDeleteProduct.setOnClickListener {
            onItemClickListenerDelete.let {
                it(products)
                listProductsInCart.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    private var onItemClickListenerDelete: (ProductsInCart)->Unit = { products: ProductsInCart -> Unit }

    fun setOnItemClickListenerDelete(listener: (ProductsInCart) ->Unit) {
        onItemClickListenerDelete = listener
    }

    private var onItemClickListenerPlus: (ProductsInCart)->Unit = { products: ProductsInCart -> Unit }

    fun setOnItemClickListenerPlus(listener: (ProductsInCart) ->Unit) {
        onItemClickListenerPlus = listener
    }

    private var onItemClickListenerMinus: (ProductsInCart)->Unit = { products: ProductsInCart -> Unit }

    fun setOnItemClickListenerMinus(listener: (ProductsInCart) ->Unit) {
        onItemClickListenerMinus = listener
    }
}
