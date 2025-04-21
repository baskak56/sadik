package com.example.myapplication.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemList: List<Item>
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

        itemList = listOf(
            Item(R.drawable.luk, "Лук", 40),
            Item(R.drawable.defaultpomidor, "Помидор", 24, false, "Кирюхин помидор", R.drawable.detailpomidor),
            Item(R.drawable.luk, "Огурец", 15),
            Item(R.drawable.luk, "Базилик", 22),
            Item(R.drawable.luk, "Картофель", 77),
            Item(R.drawable.luk, "Кабачок", 24),
            Item(R.drawable.luk, "Морковь", 41),
            Item(R.drawable.luk, "Горох", 81)
        )

        adapter = ItemAdapter(itemList, { item ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("title", item.title)
                putExtra("calories", item.calories)
                putExtra("imageResId", item.imageResId)
                putExtra("detailedDescription", item.detailedDescription)
                putExtra("detailedImageResId", item.detailedImageResId)
            }
            startActivity(intent)
        }, requireContext())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val searchListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().lowercase()
                val filtered = itemList.filter {
                    it.title.lowercase().contains(query)
                }
                adapter.updateItems(filtered)
                return true
            }
        }

        binding.searchView.setOnQueryTextListener(searchListener)

        binding.btnAll.setOnClickListener {
            adapter.updateItems(itemList)
        }

        binding.btnFavorites.setOnClickListener {
            val favorites = itemList.filter { item ->
                requireContext().getSharedPreferences("favorites", 0)
                    .getBoolean(item.title, false)
            }
            adapter.updateItems(favorites)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
