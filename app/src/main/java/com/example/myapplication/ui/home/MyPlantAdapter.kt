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
import com.example.myapplication.data.GardenStorage

class MyPlantAdapter(private var itemList: MutableList<Item>, // MutableList, чтобы изменять список
                     private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<MyPlantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewCalories: TextView = view.findViewById(R.id.textViewCalories)
        val deleteButton: ImageButton = view.findViewById(R.id.btnDelete)
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
            onItemClick(item)  // Передаем выбранный элемент
        }

        // Обработчик клика по кнопке удаления
        holder.deleteButton.setOnClickListener {
            if (position >= 0 && position < itemList.size) {
                // Удаляем элемент из списка
                val removedItem = itemList[position]

                // Удаляем элемент из глобального хранилища
                GardenStorage.plantedItems.remove(removedItem)

                // Удаляем элемент из списка адаптера
                itemList.removeAt(position)

                // Обновляем адаптер после удаления элемента
                notifyItemRemoved(position)

                // Если после удаления список пуст, обновляем весь адаптер
                if (itemList.isEmpty()) {
                    notifyDataSetChanged()
                } else {
                    // Если удалён не последний элемент, обновляем остальные
                    if (position < itemList.size) {
                        notifyItemRangeChanged(position, itemList.size - position)
                    }
                }
            }
        }
    }

    // Метод для обновления данных
    fun updateItems(newList: List<Item>) {
        itemList = newList.toMutableList()  // Преобразуем в MutableList
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size
}


