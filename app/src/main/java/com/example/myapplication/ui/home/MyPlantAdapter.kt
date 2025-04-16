package com.example.gardenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.GardenStorage
import com.example.myapplication.ui.dashboard.Item

class MyPlantAdapter(
    private var itemList: MutableList<Item>,
    private val onItemClick: (Item) -> Unit,
    private val onUpdateDecorators: () -> Unit
) : RecyclerView.Adapter<MyPlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewCalories: TextView = view.findViewById(R.id.textViewCalories)
        val deleteButton: ImageButton = view.findViewById(R.id.btnDelete)
        val favoritebtn: ImageButton = view.findViewById(R.id.btnFavorite)
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

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        holder.deleteButton.setOnClickListener {
            if (position >= 0 && position < itemList.size) {
                GardenStorage.plantedItems.remove(item)
                itemList.removeAt(position)
                notifyItemRemoved(position)
                onUpdateDecorators()
            }
        }

        holder.favoritebtn.visibility = View.GONE
    }

    fun updateItems(newList: List<Item>) {
        itemList = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size
}

