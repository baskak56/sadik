package com.example.myapplication.ui.home

import TaskDecorator
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyPlantAdapter
    private val decorators = mutableListOf<TaskDecorator>()

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
            onUpdateDecorators = { updateCalendarDecorators() }
        )

        binding.gardenRecyclerView.adapter = adapter
        binding.gardenRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        updateCalendarDecorators()

        binding.btnAddPlant.setOnClickListener {
            val plantNames = listOf("Морковь", "Огурец", "Картофель", "Помидор")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Выберите овощ")

            builder.setItems(plantNames.toTypedArray()) { _, which ->
                val selectedPlantName = plantNames[which]
                val selectedPlant: Item

                when (selectedPlantName) {
                    "Морковь" -> {
                        val dates = generateTaskMap(CalendarDay.today(), 3, 10, listOf("Полив", "Прополка"))
                        selectedPlant = Item(R.drawable.luk, "Морковь", 41, taskDates = dates)
                    }
                    "Помидор" -> {
                        val dates = generateTaskMap(CalendarDay.today(), 2, 5, listOf("Удобрение", "Полив"))
                        selectedPlant = Item(R.drawable.detailpomidor, "Помидор", 42, taskDates = dates)
                    }
                    "Картофель" -> {
                        val dates = generateTaskMap(CalendarDay.today(), 5, 4, listOf("Подгребание", "Полив"))
                        selectedPlant = Item(R.drawable.luk, "Картофель", 70, taskDates = dates)
                    }
                    else -> { // Огурец
                        val dates = generateTaskMap(CalendarDay.today(), 1, 3, listOf("Полив"))
                        selectedPlant = Item(R.drawable.luk, "Огурец", 43, taskDates = dates)
                    }

                }
                Log.d("TASK_CHECK", "${selectedPlant.title}: ${selectedPlant.taskDates}")
                GardenStorage.plantedItems.add(selectedPlant)
                adapter.updateItems(GardenStorage.plantedItems.toMutableList())
                updateCalendarDecorators()

                Toast.makeText(
                    requireContext(),
                    "Вы добавили $selectedPlantName!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            builder.setNegativeButton("Отмена", null)
            builder.show()
        }

        binding.calendarView.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                val intent = Intent(requireContext(), DateActivity::class.java)
                intent.putExtra("selectedDate", date.toString())
                startActivity(intent)
            }
        }

        return root
    }

    private fun updateCalendarDecorators() {
        decorators.forEach { binding.calendarView.removeDecorator(it) }
        decorators.clear()

        GardenStorage.plantedItems.forEach { item ->
            val dates = item.taskDates.keys
            if (dates.isNotEmpty()) {
                val decorator = TaskDecorator(HashSet(dates), Color.RED)
                binding.calendarView.addDecorator(decorator)
                decorators.add(decorator)
            }
        }
    }

    private fun generateTaskMap(
        startDate: CalendarDay,
        intervalDays: Int,
        count: Int,
        tasks: List<String>
    ): Map<CalendarDay, List<String>> {
        val taskMap = mutableMapOf<CalendarDay, List<String>>()
        val calendar = Calendar.getInstance()
        calendar.set(startDate.year, startDate.month - 1, startDate.day)

        repeat(count) {
            val newDate = CalendarDay.from(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            taskMap[newDate] = tasks
            calendar.add(Calendar.DAY_OF_MONTH, intervalDays)
        }

        return taskMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        adapter.updateItems(GardenStorage.plantedItems.toMutableList())
        updateCalendarDecorators()
    }
}

