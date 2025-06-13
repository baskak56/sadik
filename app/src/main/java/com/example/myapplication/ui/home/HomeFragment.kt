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
    private lateinit var gardenBedView: GardenBedView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Инициализация грядки
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
            val plantNames = listOf(
                "Огурец",
                "Картофель",
                "Помидор",
                "Капуста белокочанная",
                "Лук репчатый",
                "Чеснок",
                "Свекла",
                "Тыква",
                "Кабачок",
                "Перец сладкий",
                "Редис",
                "Горох",
                "Укроп",
                "Петрушка",
                "Сельдерей",
                "Редька",
                "Репа",
                "Патиссон",
                "Фасоль",
                "Кукуруза",
                "Брокколи",
                "Цветная капуста",
                "Брюссельская капуста",
                "Кольраби",
                "Шпинат",
                "Щавель",
                "Салат листовой",
                "Руккола",
                "Базилик",
                "Кинза",
                "Мята",
                "Тимьян",
                "Розмарин",
                "Арбуз",
                "Дыня",
                "Артишок",
                "Спаржа",
                "Ревень",
                "Хрен",
                "Топинамбур",
                "Батат",
                "Лук-порей",
                "Лук-шалот",
                "Черемша",
                "Кресс-салат",
                "Портулак",
                "Мангольд",
                "Баклажан",
                "Пекинская капуста")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Выберите овощ")
            builder.setItems(plantNames.toTypedArray()) { _, which ->
                val selectedPlantName = plantNames[which]
                val selectedPlant: Item

                when (selectedPlantName) {
                    "Огурец" -> {
                        val intervals = mapOf("Полив" to 2, "Подкормка" to 10, "Пасынкование" to 7)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultogurec, "Огурец", 20, taskDates = dates)
                    }
                    "Картофель" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15, "Прополка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkartofel, "Картофель", 30, taskDates = dates)
                    }
                    "Помидор" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 14, "Пасынкование" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultpomidor, "Помидор", 25, taskDates = dates)
                    }
                    "Капуста белокочанная" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 20, "Прополка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkapusta, "Капуста белокочанная", 35, taskDates = dates)
                    }
                    "Лук репчатый" -> {
                        val intervals = mapOf("Полив" to 5, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultluk, "Лук репчатый", 22, taskDates = dates)
                    }
                    "Чеснок" -> {
                        val intervals = mapOf("Полив" to 6, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultchesnok, "Чеснок", 25, taskDates = dates)
                    }
                    "Свекла" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultsvekla, "Свекла", 28, taskDates = dates)
                    }
                    "Тыква" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaulttykva, "Тыква", 30, taskDates = dates)
                    }
                    "Кабачок" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkabachok, "Кабачок", 22, taskDates = dates)
                    }
                    "Перец сладкий" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 14, "Пасынкование" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultperec, "Перец сладкий", 25, taskDates = dates)
                    }
                    "Редис" -> {
                        val intervals = mapOf("Полив" to 2, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultredis, "Редис", 18, taskDates = dates)
                    }
                    "Горох" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultgoroh, "Горох", 30, taskDates = dates)
                    }
                    "Укроп" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultukrop, "Укроп", 20, taskDates = dates)
                    }
                    "Петрушка" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultpetrushka, "Петрушка", 25, taskDates = dates)
                    }
                    "Сельдерей" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultselderey, "Сельдерей", 30, taskDates = dates)
                    }
                    "Редька" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultredka, "Редька", 22, taskDates = dates)
                    }
                    "Репа" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultrepa, "Репа", 25, taskDates = dates)
                    }
                    "Патиссон" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultpatisson, "Патиссон", 22, taskDates = dates)
                    }
                    "Фасоль" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultfasol, "Фасоль", 28, taskDates = dates)
                    }
                    "Кукуруза" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkukuruza, "Кукуруза", 35, taskDates = dates)
                    }
                    "Брокколи" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 20)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultbrokkoli, "Брокколи", 35, taskDates = dates)
                    }
                    "Цветная капуста" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 20)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultcvetnaya, "Цветная капуста", 35, taskDates = dates)
                    }
                    "Брюссельская капуста" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 20)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultbrussels, "Брюссельская капуста", 35, taskDates = dates)
                    }
                    "Кольраби" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 18)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkolrabi, "Кольраби", 30, taskDates = dates)
                    }
                    "Шпинат" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultshpinat, "Шпинат", 22, taskDates = dates)
                    }
                    "Щавель" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultshavel, "Щавель", 25, taskDates = dates)
                    }
                    "Салат листовой" -> {
                        val intervals = mapOf("Полив" to 2, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultsalat, "Салат листовой", 18, taskDates = dates)
                    }
                    "Руккола" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultrukkola, "Руккола", 20, taskDates = dates)
                    }
                    "Базилик" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultbasil, "Базилик", 22, taskDates = dates)
                    }
                    "Кинза" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkinza, "Кинза", 22, taskDates = dates)
                    }
                    "Мята" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultmint, "Мята", 25, taskDates = dates)
                    }
                    "Тимьян" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultthyme, "Тимьян", 25, taskDates = dates)
                    }
                    "Розмарин" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 20)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultrhubarb, "Розмарин", 28, taskDates = dates)
                    }
                    "Арбуз" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultwatermelon, "Арбуз", 30, taskDates = dates)
                    }
                    "Дыня" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultmelon, "Дыня", 28, taskDates = dates)
                    }
                    "Артишок" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultartichoke, "Артишок", 35, taskDates = dates)
                    }
                    "Спаржа" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultshavel, "Спаржа", 28, taskDates = dates)
                    }
                    "Ревень" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultrosemary, "Ревень", 25, taskDates = dates)
                    }
                    "Хрен" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaulthren, "Хрен", 30, taskDates = dates)
                    }
                    "Топинамбур" -> {
                        val intervals = mapOf("Полив" to 5, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaulttopinambur, "Топинамбур", 32, taskDates = dates)
                    }
                    "Батат" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultbatat, "Батат", 30, taskDates = dates)
                    }
                    "Лук-порей" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultporey, "Лук-порей", 28, taskDates = dates)
                    }
                    "Лук-шалот" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultshalot, "Лук-шалот", 27, taskDates = dates)
                    }
                    "Черемша" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultcheremsha, "Черемша", 25, taskDates = dates)
                    }
                    "Кресс-салат" -> {
                        val intervals = mapOf("Полив" to 2, "Подкормка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkress, "Кресс-салат", 20, taskDates = dates)
                    }
                    "Портулак" -> {
                        val intervals = mapOf("Полив" to 2, "Подкормка" to 12)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultportulak, "Портулак", 20, taskDates = dates)
                    }
                    "Мангольд" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultmangold, "Мангольд", 25, taskDates = dates)
                    }
                    "Баклажан" -> {
                        val intervals = mapOf("Полив" to 4, "Подкормка" to 15, "Пасынкование" to 14)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultbaklashan, "Баклажан", 30, taskDates = dates)
                    }
                    "Пекинская капуста" -> {
                        val intervals = mapOf("Полив" to 3, "Подкормка" to 20, "Прополка" to 10)
                        val dates = generateTaskMap(CalendarDay.today(), intervals, 30)
                        selectedPlant = Item(R.drawable.defaultkapusta, "Пекинская капуста", 30, taskDates = dates)
                    }
                    else -> {
                        selectedPlant = Item(R.drawable.defaultluk, "Неизвестное растение", 0)
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
        binding.btnStrongWind.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Подтверждение")
                .setMessage("Был сильный ветер? Добавить задачи на подвязку и осмотр растений?")
                .setPositiveButton("Да") { _, _ ->
                    addWindEmergencyTasks()
                    Toast.makeText(requireContext(), "Добавлены задачи на подвязку", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Отмена", null)
                .show()
        }

        binding.btnHail.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Подтверждение")
                .setMessage("Был град? Добавить срочные задачи по осмотру и восстановлению растений?")
                .setPositiveButton("Да") { _, _ ->
                    addHailEmergencyTasks()
                    Toast.makeText(requireContext(), "Добавлены задачи после града", Toast.LENGTH_SHORT).show()
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
        gardenBedView.setPlants(GardenStorage.plantedItems)
    }

    private fun updateCalendarDecorators() {
        binding.calendarView.removeDecorators()

        val allDates = mutableSetOf<CalendarDay>()

        GardenStorage.plantedItems.forEach { item ->
            for (dateStr in item.taskDates.keys) {
                val parts = dateStr.split("-")
                if (parts.size == 3) {
                    val day = CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                    allDates.add(day)
                }
            }
        }

        if (allDates.isNotEmpty()) {
            val decorator = TaskDecorator(HashSet(allDates), Color.RED)
            binding.calendarView.addDecorator(decorator)
        }
    }


    // Новые методы для добавления задач
    private fun addWindEmergencyTasks() {
        val today = CalendarDay.today()
        val todayKey = "${today.year}-${today.month}-${today.day}"

        GardenStorage.plantedItems = GardenStorage.plantedItems.map { plant ->
            val newTasks = mutableListOf(
                TaskInfo("Подвязка",Color.RED),
                TaskInfo("Осмотр после ветра", Color.RED)
            )

            val existingTasks = plant.taskDates[todayKey] ?: emptyList()
            val updatedTasks = existingTasks + newTasks

            val newTaskDates = plant.taskDates.toMutableMap().apply {
                put(todayKey, updatedTasks)
            }

            plant.copy(taskDates = newTaskDates)
        }.toMutableList()


        updateGardenData()
    }

    private fun addHailEmergencyTasks() {
        val today = CalendarDay.today()
        val todayKey = "${today.year}-${today.month}-${today.day}"
        val tomorrow = CalendarDay.from(today.year, today.month, today.day + 1)
        val tomorrowKey = "${tomorrow.year}-${tomorrow.month}-${tomorrow.day}"

        GardenStorage.plantedItems = GardenStorage.plantedItems.map { plant ->
            // Задачи на сегодня
            val todayTasks = mutableListOf(
                TaskInfo("Срочный осмотр после града", Color.RED),
                TaskInfo("Удаление повреждений", Color.RED)
            )

            // Задачи на завтра
            val tomorrowTasks = mutableListOf(
                TaskInfo("Обработка антистрессом", Color.RED),
                TaskInfo("Проверка восстановления", Color.RED)
            )

            val newTaskDates = plant.taskDates.toMutableMap().apply {
                put(todayKey, (get(todayKey) ?: emptyList()) + todayTasks)
                put(tomorrowKey, (get(tomorrowKey) ?: emptyList()) + tomorrowTasks)
            }

            plant.copy(taskDates = newTaskDates)
        }.toMutableList()

        updateGardenData()
    }
    private fun updateGardenData() {
        StorageHelper.savePlants(requireContext(), GardenStorage.plantedItems)
        adapter.updateItems(GardenStorage.plantedItems.toMutableList())
        gardenBedView.setPlants(GardenStorage.plantedItems)
        updateCalendarDecorators()
    }

    private fun generateTaskMap(
        startDate: CalendarDay,
        intervals: Map<String, Int>,
        count: Int
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
                val info = TaskInfo(taskName, Color.RED)
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
