package com.example.laptop_app.adapter.rcv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.model.Product

class LaptopAdapter (
    private var context: Context,
    private var products: List<Product>,
    private var clickItem: ClickItem
        ): RecyclerView.Adapter<LaptopAdapter.LaptopViewHolder>() {
    class LaptopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface ClickItem {
        fun clickItem(product: Product): Unit
    }

    fun setProduct(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaptopViewHolder =
        LaptopViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_products, parent, false)
        )

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: LaptopViewHolder, position: Int) {
        holder.itemView.apply {
            // init
            val imageView: ImageView = holder.itemView.findViewById(R.id.imageProductItem)
            val textProductName: TextView = holder.itemView.findViewById(R.id.textProductName)
            val textPriceProduct: TextView = holder.itemView.findViewById(R.id.textPriceProduct)
            val textDiscountProduct: TextView = holder.itemView.findViewById(R.id.textDiscountProduct)
            val containerItem: LinearLayout = holder.itemView.findViewById(R.id.containerItemProduct)

            // get product current
            val product: Product = products[position]
            if (product == null) return

            // show data
            Glide.with(context)
                .load(product.getImage())
                .error(R.mipmap.ic_launcher)
                .into(imageView)
            if (product.getName().length > 13) {
                textProductName.text = "${product.getName().substring(0, 13)}..."
            } else {
                textProductName.text = product.getName()
            }
            textPriceProduct.text = "$${product.getTax()}"
            if (product.getDiscount() > 0) {
                textDiscountProduct.visibility = View.VISIBLE
                textDiscountProduct.text = "-${product.getDiscount()}%"
            } else {
                textDiscountProduct.visibility = View.GONE
            }

            // set onclick
            containerItem.setOnClickListener { clickItem.clickItem(product) }
        }
    }
}