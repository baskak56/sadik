package com.example.myapplication.ui.home

import HarvestDecorator
import TaskDecorator
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
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
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyPlantAdapter
    private val decorators = mutableListOf<DayViewDecorator>()
    private lateinit var gardenBedView: GardenBedView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        gardenBedView = binding.gardenBedView

        GardenStorage.plantedItems = StorageHelper.loadPlants(requireContext()).toMutableList()
        gardenBedView.setPlants(GardenStorage.plantedItems)

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

                val today = Calendar.getInstance()

                when (selectedPlantName) {
                    "Морковь" -> {
                        val intervals = mapOf("Полив" to 1, "Прополка" to 3, "Подкормка" to 5)
                        val colors = mapOf("Полив" to Color.BLUE, "Прополка" to Color.GREEN, "Подкормка" to Color.MAGENTA)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 10, colors)

                        val harvestCalendar = (today.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, 70) }
                        val harvestDate = CalendarDay.from(
                            harvestCalendar.get(Calendar.YEAR),
                            harvestCalendar.get(Calendar.MONTH) + 1,
                            harvestCalendar.get(Calendar.DAY_OF_MONTH)
                        )

                        selectedPlant = Item(R.drawable.defaultmorkov, "Морковь", 41, taskDates = dates, harvestDate = harvestDate)
                    }
                    "Помидор" -> {
                        val intervals = mapOf("Удобрение" to 5, "Полив" to 2)
                        val colors = mapOf("Удобрение" to Color.YELLOW, "Полив" to Color.CYAN)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 10, colors)

                        val harvestCalendar = (today.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, 80) }
                        val harvestDate = CalendarDay.from(
                            harvestCalendar.get(Calendar.YEAR),
                            harvestCalendar.get(Calendar.MONTH) + 2,
                            harvestCalendar.get(Calendar.DAY_OF_MONTH)
                        )

                        selectedPlant = Item(R.drawable.defaultpomidor, "Помидор", 42, taskDates = dates, harvestDate = harvestDate)
                    }
                    "Картофель" -> {
                        val intervals = mapOf("Окучивание" to 5, "Полив" to 4)
                        val colors = mapOf("Окучивание" to Color.LTGRAY, "Полив" to Color.BLUE)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 10, colors)

                        val harvestCalendar = (today.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, 90) }
                        val harvestDate = CalendarDay.from(
                            harvestCalendar.get(Calendar.YEAR),
                            harvestCalendar.get(Calendar.MONTH) + 3,
                            harvestCalendar.get(Calendar.DAY_OF_MONTH)
                        )

                        selectedPlant = Item(R.drawable.defaultkartofel, "Картофель", 70, taskDates = dates, harvestDate = harvestDate)
                    }
                    else -> {
                        val intervals = mapOf("Полив" to 1)
                        val colors = mapOf("Полив" to Color.GREEN)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 10, colors)

                        val harvestCalendar = (today.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, 50) }
                        val harvestDate = CalendarDay.from(
                            harvestCalendar.get(Calendar.YEAR),
                            harvestCalendar.get(Calendar.MONTH) + 4,
                            harvestCalendar.get(Calendar.DAY_OF_MONTH)
                        )

                        selectedPlant = Item(R.drawable.defaultogurec, "Огурец", 43, taskDates = dates, harvestDate = harvestDate)
                    }
                }

                GardenStorage.plantedItems.add(selectedPlant)
                StorageHelper.savePlants(requireContext(), GardenStorage.plantedItems)
                adapter.updateItems(GardenStorage.plantedItems.toMutableList())
                gardenBedView.setPlants(GardenStorage.plantedItems)
                updateCalendarDecorators()

                Toast.makeText(requireContext(), "Вы добавили $selectedPlantName!", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Отмена", null)
            builder.show()
        }

        binding.btnRainToday.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Подтверждение")
                .setMessage("Вы уверены, что сегодня был дождь? Это удалит задачи полива на сегодня и завтра.")
                .setPositiveButton("Да") { _, _ ->
                    removeWateringTasksForTodayAndTomorrow()
                    Toast.makeText(requireContext(), "Задачи полива удалены", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Отмена", null)
                .show()
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

    private fun removeWateringTasksForTodayAndTomorrow() {
        val today = CalendarDay.today()
        val tomorrow = CalendarDay.from(today.year, today.month, today.day + 1)

        val todayKey = "${today.year}-${today.month}-${today.day}"
        val tomorrowKey = "${tomorrow.year}-${tomorrow.month}-${tomorrow.day}"

        val updatedPlants = GardenStorage.plantedItems.map { plant ->
            val mutableMap = plant.taskDates.mapValues { it.value.toMutableList() }.toMutableMap()

            mutableMap[todayKey]?.removeAll { it.name.equals("Полив", ignoreCase = true) }
            mutableMap[tomorrowKey]?.removeAll { it.name.equals("Полив", ignoreCase = true) }

            if (mutableMap[todayKey]?.isEmpty() == true) mutableMap.remove(todayKey)
            if (mutableMap[tomorrowKey]?.isEmpty() == true) mutableMap.remove(tomorrowKey)

            plant.copy(taskDates = mutableMap)
        }

        GardenStorage.plantedItems = updatedPlants.toMutableList()
        StorageHelper.savePlants(requireContext(), GardenStorage.plantedItems)
        adapter.updateItems(GardenStorage.plantedItems.toMutableList())
        updateCalendarDecorators()
        gardenBedView.invalidate()
    }

    private fun updateCalendarDecorators() {
        decorators.forEach { binding.calendarView.removeDecorator(it) }
        decorators.clear()

        GardenStorage.plantedItems.forEach { item ->
            for ((dateStr, taskList) in item.taskDates) {
                val parts = dateStr.split("-")
                if (parts.size == 3) {
                    val calendarDay = CalendarDay.from(
                        parts[0].toInt(),
                        parts[1].toInt(),
                        parts[2].toInt()
                    )
                    for (task in taskList) {
                        val decorator = TaskDecorator(hashSetOf(calendarDay), task.color)
                        binding.calendarView.addDecorator(decorator)
                        decorators.add(decorator)
                    }
                }
            }

            item.harvestDate?.let { harvestDate ->
                val harvestDecorator = HarvestDecorator(requireContext(), hashSetOf(harvestDate))
                binding.calendarView.addDecorator(harvestDecorator)
                decorators.add(harvestDecorator)
            }
        }
    }

    private fun generateTaskMap(
        startDate: CalendarDay,
        intervals: Map<String, Int>,
        count: Int,
        taskColors: Map<String, Int>
    ): Map<String, List<TaskInfo>> {
        val taskMap = mutableMapOf<String, MutableList<TaskInfo>>()

        for ((taskName, intervalDays) in intervals) {
            val calendar = Calendar.getInstance()
            calendar.set(startDate.year, startDate.month - 1, startDate.day)

            repeat(count) {
                val date = CalendarDay.from(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                val info = TaskInfo(taskName, taskColors[taskName] ?: Color.RED)
                val key = "${date.year}-${date.month}-${date.day}"
                taskMap.getOrPut(key) { mutableListOf() }.add(info)
                calendar.add(Calendar.DAY_OF_MONTH, intervalDays)
            }
        }

        return taskMap
    }

    override fun onResume() {
        super.onResume()
        adapter.updateItems(GardenStorage.plantedItems.toMutableList())
        gardenBedView.setPlants(GardenStorage.plantedItems)
        updateCalendarDecorators()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
