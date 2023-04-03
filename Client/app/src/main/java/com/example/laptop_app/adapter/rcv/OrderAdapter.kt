package com.example.laptop_app.adapter.rcv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.model.Order
import com.example.laptop_app.model.Product
import com.example.laptop_app.model.respone.ResOrder
import com.example.laptop_app.others.Const

class OrderAdapter (
    private var orders: List<ResOrder>,
    private var context: Context,
    private var clickItem: ClickItem
        ): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}
    interface ClickItem {
        fun buyAgain(product: Product)
    }

    public fun setOrders(orders: List<ResOrder>): Unit {
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_orders, parent, false)
        )

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.itemView.apply {
            val imageProduct: ImageView = holder.itemView.findViewById(R.id.imageProduct)
            val textProductName: TextView = holder.itemView.findViewById(R.id.textProductName)
            val textProductQuantity_1: TextView = holder.itemView.findViewById(R.id.textProductQuantity_1)
            val layoutDiscount: ConstraintLayout = holder.itemView.findViewById(R.id.layoutDiscount)
            val textOldPrice: TextView = holder.itemView.findViewById(R.id.textOldPrice)
            val textNewPrice: TextView = holder.itemView.findViewById(R.id.textNewPrice)
            val textProductQuantity_2: TextView = holder.itemView.findViewById(R.id.textProductQuantity_2)
            val textSumPrice: TextView = holder.itemView.findViewById(R.id.textSumPrice)
            val textStatusOrder: TextView = holder.itemView.findViewById(R.id.textStatusOrder)
            val buttonBuyAgain: Button = holder.itemView.findViewById(R.id.buttonBuyAgain)

            val order: ResOrder = orders[position]
            if (order == null) return

            Glide.with(context)
                .load(order.getProduct().getImage())
                .error(R.mipmap.ic_launcher)
                .into(imageProduct)
            textProductName.text = order.getProduct().getName()
            textProductQuantity_1.text = "x${order.getCart().getProductQuantity()}"
            if (order.getProduct().getDiscount() > 0) {
                layoutDiscount.visibility = View.VISIBLE
                textOldPrice.text = "$${order.getProduct().getPrice()}"
            } else {
                layoutDiscount.visibility = View.GONE
            }
            textNewPrice.text = "$${order.getProduct().getTax()}"
            textProductQuantity_2.text = "Số lượng: ${order.getCart().getProductQuantity()}"
            when (order.getShippingStatus()) {
                Const.waitingConfirm -> textStatusOrder.text = "Trạng thái: Chờ xác nhận"
                Const.shipping -> textStatusOrder.text = "Trạng thái: Đang giao"
                Const.successfulDelivery -> textStatusOrder.text = "Trạng thái: Giao thành công"
                else -> textStatusOrder.text = "Trạng thái: Chờ xác nhận"
            }
            // tinh tong tien
            var sum: Double = order.getProduct().getTax() * order.getCart().getProductQuantity()
            textSumPrice.text = "$${sum}"

            buttonBuyAgain.setOnClickListener { clickItem.buyAgain(order.getProduct()) }
        }
    }
}