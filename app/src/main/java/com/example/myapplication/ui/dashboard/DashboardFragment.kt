package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

        val itemList = listOf(
            Item(R.drawable.luk, "Лук", 40, true),
            Item(R.drawable.defaultpomidor, "Помидор", 24, false, "Кирюхин помидор", R.drawable.detailpomidor),
            Item(R.drawable.luk, "Огурец", 15, true),
            Item(R.drawable.luk, "Базилик", 22),
            Item(R.drawable.luk, "Картофель", 77),
            Item(R.drawable.luk, "Кабачок", 24),
            Item(R.drawable.luk, "Морковь", 41, true),
            Item(R.drawable.luk, "Горох", 81)
        )

        var filteredItemList: List<Item> = itemList

        val adapter = ItemAdapter(filteredItemList) { item ->
            val bundle = Bundle().apply {
                putString("title", item.title)
                putInt("calories", item.calories)
                putInt("imageResId", item.imageResId)
                putString("detailedDescription", item.detailedDescription)
                putInt("detailedImageResId", item.detailedImageResId)
            }

            findNavController().navigate(R.id.action_dashboard_to_detail, bundle)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // Исправленный обработчик для поиска с использованием объекта, реализующего интерфейс
        val searchListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Не требуется действие при отправке текста
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Фильтрация элементов на основе введенного текста
                val query = newText.orEmpty().toLowerCase()
                filteredItemList = itemList.filter { item ->
                    item.title.toLowerCase().contains(query)
                }
                adapter.updateItems(filteredItemList)
                return true
            }
        }

        binding.searchView.setOnQueryTextListener(searchListener)

        binding.btnAll.setOnClickListener {
            filteredItemList = itemList
            adapter.updateItems(filteredItemList)
        }

        binding.btnFavorites.setOnClickListener {
            filteredItemList = itemList.filter { it.favorite }
            adapter.updateItems(filteredItemList)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
