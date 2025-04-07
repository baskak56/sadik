package com.example.gardenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.dashboard.Item

class ItemAdapter(private var itemList: List<Item>,
                  private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewCalories: TextView = view.findViewById(R.id.textViewCalories)
        val btnFavorite: ImageButton = view.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textViewTitle.text = item.title
        holder.textViewCalories.text = "${item.calories} ккал"

        // Обновляем иконку звезды в зависимости от избранного
        holder.btnFavorite.setImageResource(
            if (item.favorite) R.drawable.ic_home_black_24dp
            else R.drawable.baseline_bungalow_24
        )
        holder.itemView.setOnClickListener {
            onItemClick(item)  // Передаем выбранный элемент
        }
        // Обработчик клика по звезде
        holder.btnFavorite.setOnClickListener {
            item.favorite = !item.favorite
            notifyItemChanged(position)
        }
    }
    fun updateItems(newList: List<Item>) {
        itemList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size
}