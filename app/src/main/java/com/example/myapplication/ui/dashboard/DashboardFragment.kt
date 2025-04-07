package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemAdapter
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

        val itemList = listOf(
            Item(R.drawable.luk, "Лук", 40, true),
            Item(R.drawable.defaultpomidor, "Помидор", 24,false,"Кирюхин помидор", R.drawable.detailpomidor),
            Item(R.drawable.luk, "Огурец", 15, true),
            Item(R.drawable.luk, "Базилик", 22),
            Item(R.drawable.luk, "Картофель", 77),
            Item(R.drawable.luk, "Кабачок", 24),
            Item(R.drawable.luk, "Морковь", 41, true),
            Item(R.drawable.luk, "Горох", 81)
        )

        var filteredItemList: List<Item> = itemList

        val adapter = ItemAdapter(filteredItemList) { item ->
            val detailFragment = DetailFragment.newInstance(
                item.title,
                item.calories,
                item.imageResId,
                item.detailedDescription,
                item.detailedImageResId
            )

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)  // Добавляем в бэкстек для возврата
                .commit()

            recyclerView.visibility = View.GONE
            binding.btnAll.visibility = View.GONE
            binding.btnFavorites.visibility = View.GONE
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

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
