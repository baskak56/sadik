package com.example.gardenapp

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.dashboard.Item

class ItemAdapter(
    private var itemList: List<Item>,
    private val onItemClick: (Item) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val prefs: SharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewCalories: TextView = view.findViewById(R.id.textViewCalories)
        val btnFavorite: ImageButton = view.findViewById(R.id.btnFavorite)
        val deleteButton: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        item.favorite = prefs.getBoolean(item.title, false)

        holder.imageView.setImageResource(item.imageResId)
        holder.textViewTitle.text = item.title
        holder.textViewCalories.text = "${item.calories} ккал"

        holder.btnFavorite.setImageResource(
            if (item.favorite) R.drawable.ic_home_black_24dp
            else R.drawable.starcardlogo
        )

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        holder.btnFavorite.setOnClickListener {
            item.favorite = !item.favorite
            prefs.edit().putBoolean(item.title, item.favorite).apply()
            notifyItemChanged(position)
        }

        holder.deleteButton.visibility = View.GONE
    }

    fun updateItems(newList: List<Item>) {
        itemList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size
}
