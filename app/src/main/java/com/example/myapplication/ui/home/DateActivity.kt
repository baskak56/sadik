package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.GardenStorage
import com.example.myapplication.ui.home.HomeFragment
import com.prolificinteractive.materialcalendarview.CalendarDay

class DateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        val selectedDateStr = intent.getStringExtra("selectedDate") ?: ""
        val selectedDate = parseCalendarDay(selectedDateStr)

        val textView: TextView = findViewById(R.id.textViewDate)
        textView.text = "Дата: ${formatCalendarDay(selectedDateStr)}\n"

        val plantTasks = getPlantTasksForDate(selectedDate)

        val tasksTextView: TextView = findViewById(R.id.textViewTasks)
        tasksTextView.text = if (plantTasks.isEmpty()) {
            "На этот день нет задач."
        } else {
            plantTasks.joinToString("\n")
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

    private fun getPlantTasksForDate(selectedDate: CalendarDay?): List<String> {
        if (selectedDate == null) return emptyList()
        val tasks = mutableListOf<String>()
        val plantList = GardenStorage.plantedItems

        plantList.forEach { item ->
            item.taskDates.forEach { (taskName, dates) ->
                if (selectedDate in dates) {
                    tasks.add("$taskName (${item.title})")
                }
            }
        }

        return tasks
    }
    override fun onBackPressed() {
        // Здесь можно обновить данные, если нужно
        setResult(RESULT_OK)
        super.onBackPressed() // Вызов метода родителя
    }

}
