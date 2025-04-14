package com.example.myapplication.ui.home

import TaskDecorator
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.gardenapp.MyPlantAdapter
import com.example.myapplication.DateActivity
import com.example.myapplication.data.GardenStorage
import com.example.myapplication.ui.dashboard.Item
import com.prolificinteractive.materialcalendarview.CalendarDay

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyPlantAdapter
    private val decorators = mutableMapOf<Item, TaskDecorator>() // Храним декораторы для каждого растения

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = MyPlantAdapter(
            GardenStorage.plantedItems.toMutableList(),
            onItemClick = { item ->
                Toast.makeText(requireContext(), "Вы выбрали ${item.title}", Toast.LENGTH_SHORT).show()
            },
            removeDecorator = { item -> // Callback для удаления декоратора
                decorators[item]?.let {
                    binding.calendarView.removeDecorator(it)
                    decorators.remove(item)
                }
            }
        )

        binding.gardenRecyclerView.adapter = adapter
        binding.gardenRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Инициализация декораторов для уже существующих растений
        GardenStorage.plantedItems.forEach { item ->
            item.taskDates?.let { dates ->
                val decorator = TaskDecorator(HashSet(dates))
                binding.calendarView.addDecorator(decorator)
                decorators[item] = decorator
            }
        }

        binding.btnAddPlant.setOnClickListener {
            val plantNames = listOf("Морковь", "Огурец", "Картофель", "Помидор")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Выберите овощ")

            builder.setItems(plantNames.toTypedArray()) { _, which ->
                val selectedPlantName = plantNames[which]
                val selectedPlant = when (selectedPlantName) {
                    "Морковь" -> Item(R.drawable.luk, "Морковь", 41, taskDates = hashSetOf(CalendarDay.today()))
                    "Огурец" -> Item(R.drawable.defaultpomidor, "Огурец", 15, taskDates = hashSetOf(CalendarDay.from(2025, 3, 15)))
                    "Картофель" -> Item(R.drawable.detailpomidor, "Картофель", 77, taskDates = hashSetOf(CalendarDay.from(2025, 3, 22)))
                    "Помидор" -> Item(R.drawable.luk, "Помидор", 18, taskDates = hashSetOf(CalendarDay.from(2025, 4, 1)))
                    else -> Item(R.drawable.detailpomidor, "Морковь", 41, taskDates = hashSetOf(CalendarDay.today()))
                }

                GardenStorage.plantedItems.add(selectedPlant)
                adapter.updateItems(GardenStorage.plantedItems.toMutableList())

                selectedPlant.taskDates?.let { taskDates ->
                    val decorator = TaskDecorator(HashSet(taskDates))
                    binding.calendarView.addDecorator(decorator)
                    decorators[selectedPlant] = decorator // Сохраняем декоратор
                }

                Toast.makeText(
                    requireContext(),
                    "Вы добавили $selectedPlantName!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            builder.setNegativeButton("Отмена", null)
            builder.show()
        }

        // Обработка выбора даты на календаре
        binding.calendarView.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                val intent = Intent(requireContext(), DateActivity::class.java)
                intent.putExtra("selectedDate", date.toString())
                startActivity(intent)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
