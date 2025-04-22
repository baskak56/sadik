package com.example.gardenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.GardenStorage
import com.example.myapplication.ui.dashboard.Item
import com.example.myapplication.ui.home.StorageHelper

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
            // Лог до удаления
            Log.d("DEBUG", "До удаления: itemList.size = ${itemList.size}, item = ${item.title}")

            // Удаляем элемент из GardenStorage (если нужно)
            GardenStorage.plantedItems.remove(item)
            StorageHelper.savePlants(holder.itemView.context, GardenStorage.plantedItems)

            // Удаляем элемент из локального списка
            if (position >= 0 && position < itemList.size) {
                // Удаляем элемент из списка
                itemList.removeAt(position)

                // Лог после удаления
                Log.d("DEBUG", "После удаления: itemList.size = ${itemList.size}")

                // Обновляем декораторы
                onUpdateDecorators()

                // Проверяем, если список пуст, перерисовываем весь RecyclerView
                if (itemList.isEmpty()) {
                    Log.d("DEBUG", "Список пуст, перерисовываем RecyclerView.")
                    notifyDataSetChanged()  // Это перерисует весь RecyclerView
                } else {
                    // Иначе удаляем конкретный элемент из RecyclerView
                    Log.d("DEBUG", "Удаляем элемент на позиции $position.")
                    notifyItemRemoved(position)

                    // После удаления обновляем UI, чтобы скрыть удалённый элемент
                    // Используем notifyItemRangeChanged, чтобы обновить позиции элементов
                    if (position < itemList.size) {
                        notifyItemRangeChanged(position, itemList.size - position)
                    }
                }
            } else {
                Log.e("DEBUG", "Попытка удалить элемент по некорректному индексу: $position")
            }
        }


        holder.favoritebtn.visibility = View.GONE
    }

    fun updateItems(newList: List<Item>) {
        itemList = newList.toMutableList()
        Log.d("DEBUG", "updateItems: Новый список с размером ${itemList.size}")
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size
}
