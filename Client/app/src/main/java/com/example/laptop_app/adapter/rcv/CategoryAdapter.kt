package com.example.laptop_app.adapter.rcv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laptop_app.R
import com.example.laptop_app.model.Category

class CategoryAdapter (
    private val context: Context,
    private var categories: MutableList<Category>,
    private val clickItem: ClickItem
        ): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    interface ClickItem {
        fun clickItem(category: Category): Unit
    }

    public fun setCategories(categories: MutableList<Category>): Unit {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_categories, parent, false)
        )

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.itemView.apply {
            val category: Category = categories[position]
            if (category == null) return

            // init
            val containerItem: LinearLayout = holder.itemView.findViewById(R.id.itemCategory)
            val textCategory: TextView = holder.itemView.findViewById(R.id.textCategory)

            // show data
            textCategory.text = category.getName()

            // set on click
            containerItem.setOnClickListener {
                clickItem.clickItem(category)
            }
        }
    }
}