package com.example.laptop_app.adapter.rcv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.model.respone.ResCart
import com.example.laptop_app.model.Product
import com.makeramen.roundedimageview.RoundedImageView

class CartAdapter (
    private var context: Context,
    private var resCarts: List<ResCart>,
    private var clickItem: ClickItem
        ): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface ClickItem {
        fun showDetailsProduct(product: Product)
        fun riseProduct(resCart: ResCart)
        fun reduceProduct(resCart: ResCart)
    }

    fun setCarts(resCarts: List<ResCart>) {
        this.resCarts = resCarts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_carts, parent, false)
        )

    override fun getItemCount(): Int = resCarts.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.itemView.apply {
            // init view
            val image: RoundedImageView = holder.itemView.findViewById(R.id.imageProduct)
            val textName: TextView = holder.itemView.findViewById(R.id.textProductName)
            val textDescription: TextView = holder.itemView.findViewById(R.id.textProductDescription)
            val textPrice: TextView = holder.itemView.findViewById(R.id.textPrice)
            val textReduce: TextView = holder.itemView.findViewById(R.id.textReduceProduct)
            val textQuantityProduct: TextView = holder.itemView.findViewById(R.id.textQuantityProduct)
            val textRise: TextView = holder.itemView.findViewById(R.id.textRiseProduct)

            // get object
            val resCart: ResCart = resCarts[position]
            if (resCart == null) return

            // show data
            Glide.with(context)
                .load(resCart.getProduct().getImage())
                .error(R.mipmap.ic_launcher)
                .into(image)
            if (resCart.getProduct().getName().length > 11) {
                textName.text  ="${resCart.getProduct().getName()}".substring(0, 11) + "..."
            } else {
                textName.text = "${resCart.getProduct().getName()}"
            }
            if (resCart.getProduct().getDescription().length > 11) {
                textDescription.text = resCart.getProduct().getDescription().substring(0, 11) + "..."
            } else {
                textDescription.text = resCart.getProduct().getDescription()
            }
            textPrice.text = "$${resCart.getProduct().getTax()}"
            textQuantityProduct.text = "${resCart.getProductQuantity()}"

            // set on click
            textReduce.setOnClickListener { clickItem.reduceProduct(resCart) }
            textRise.setOnClickListener { clickItem.riseProduct(resCart) }
        }
    }
}