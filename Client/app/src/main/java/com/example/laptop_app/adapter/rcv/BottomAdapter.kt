package com.example.laptop_app.adapter.rcv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.laptop_app.R
import com.example.laptop_app.model.BottomBar

class BottomAdapter (
    private var context: Context,
    private var bottomBars: MutableList<BottomBar>,
    private var clickItem: ClickItem
        ): RecyclerView.Adapter<BottomAdapter.BottomViewHolder>() {
    class BottomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}
    interface ClickItem {
        fun clickItem(bottomBar: BottomBar): Unit
    }

    fun setBottomBar(bottomBars: MutableList<BottomBar>): Unit {
        this.bottomBars = bottomBars
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder =
        BottomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_bottom_bars, parent, false)
        )

    override fun getItemCount(): Int = bottomBars.size

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
        holder.itemView.apply {
            // init
            val imageView: ImageView = holder.itemView.findViewById(R.id.imageItemBar)

            val bottomBar: BottomBar = bottomBars[position]
            if (bottomBar == null) return

            imageView.setImageResource(bottomBar.getImage())

            // set onclick
            imageView.setOnClickListener { clickItem.clickItem(bottomBar) }
        }
    }
}