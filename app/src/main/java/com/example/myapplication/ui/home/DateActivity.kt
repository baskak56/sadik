package com.example.myapplication

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.GardenStorage
import com.example.myapplication.ui.home.StorageHelper
import com.example.myapplication.ui.home.TaskInfo
import com.prolificinteractive.materialcalendarview.CalendarDay

class DateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        val selectedDateStr = intent.getStringExtra("selectedDate") ?: ""
        val selectedDate = parseCalendarDay(selectedDateStr)

        val textView: TextView = findViewById(R.id.textViewDate)
        textView.text = "Дата: ${formatCalendarDay(selectedDateStr)}\n"

        val taskContainer: LinearLayout = findViewById(R.id.taskContainer)
        val plantTasks = getPlantTasksForDate(selectedDate)

        if (plantTasks.isEmpty()) {
            val emptyText = TextView(this).apply {
                text = "На этот день нет задач."
                textSize = 16f
            }
            taskContainer.addView(emptyText)
        } else {
            plantTasks.forEach { (plantTitle, taskInfo) ->
                val checkBox = CheckBox(this).apply {
                    text = "${taskInfo.name} (${plantTitle})"
                    textSize = 16f
                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                    )
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            removeTask(selectedDate!!, plantTitle, taskInfo.name)
                            taskContainer.removeView(this)

                            // Показать всплывающее сообщение
                            Toast.makeText(
                                this@DateActivity,
                                "Молодец! Так держать!",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Проверить, все ли задачи выполнены
                            if (taskContainer.childCount == 0) {
                                val allTasksCompletedText = TextView(this@DateActivity).apply {
                                    text = "Задачи выполнены!"
                                    textSize = 18f
                                    setTextColor(resources.getColor(R.color.green))
                                }
                                taskContainer.addView(allTasksCompletedText)
                            }
                        }
                    }
                }
                taskContainer.addView(checkBox)
            }
        }
    }

    private fun formatCalendarDay(calendarDay: String): String {
        val regex = """CalendarDay\{(\d{4})-(\d{1,2})-(\d{1,2})\}""".toRegex()
        val (year, month, day) = regex.find(calendarDay)?.destructured ?: return ""
        val formattedDay = day.padStart(2, '0')
        val formattedMonth = month.padStart(2, '0')
        return "$formattedDay.$formattedMonth.$year"
    }

    private fun parseCalendarDay(calendarDay: String): CalendarDay? {
        val regex = """CalendarDay\{(\d{4})-(\d{1,2})-(\d{1,2})\}""".toRegex()
        val match = regex.find(calendarDay) ?: return null
        val (year, month, day) = match.destructured
        return CalendarDay.from(year.toInt(), month.toInt(), day.toInt())
    }

    private fun getPlantTasksForDate(selectedDate: CalendarDay?): List<Pair<String, TaskInfo>> {
        if (selectedDate == null) return emptyList()

        val tasks = mutableListOf<Pair<String, TaskInfo>>()
        val selectedDateStr = "${selectedDate.year}-${selectedDate.month}-${selectedDate.day}"

        GardenStorage.plantedItems.forEach { item ->
            item.taskDates[selectedDateStr]?.forEach { task ->
                tasks.add(item.title to task)
            }
        }

        return tasks
    }

    private fun removeTask(date: CalendarDay, plantName: String, taskName: String) {
        val key = "${date.year}-${date.month}-${date.day}"
        GardenStorage.plantedItems.forEach { item ->
            if (item.title == plantName) {
                val tasks = item.taskDates[key]?.toMutableList() ?: return@forEach
                val updatedTasks = tasks.filterNot { it.name == taskName }

                // Если задач не осталось для этой даты, удаляем ее из Map
                if (updatedTasks.isEmpty()) {
                    val mutableTaskDates = item.taskDates.toMutableMap()
                    mutableTaskDates.remove(key)
                    item.taskDates = mutableTaskDates
                } else {
                    val mutableTaskDates = item.taskDates.toMutableMap()
                    mutableTaskDates[key] = updatedTasks
                    item.taskDates = mutableTaskDates
                }
            }
        }
        StorageHelper.savePlants(this, GardenStorage.plantedItems)

    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }
}
