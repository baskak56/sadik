package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        // Получаем переданную дату
        val selectedDate = intent.getStringExtra("selectedDate") ?: ""

        // Преобразуем дату в нужный формат
        val formattedDate = formatCalendarDay(selectedDate)

        // Отображаем выбранную дату в TextView
        val textView: TextView = findViewById(R.id.textViewDate)
        textView.text = formattedDate
    }

    /**
     * Преобразует строку вида "CalendarDay{2025-3-4}" в "4.3.2025"
     */
    private fun formatCalendarDay(calendarDay: String): String {
        val regex = """CalendarDay\{(\d{4})-(\d{1,2})-(\d{1,2})\}""".toRegex()
        val (year, month, day) = regex.find(calendarDay)?.destructured ?: return ""

        // Форматируем с ведущими нулями
        val formattedDay = day.padStart(2, '0')
        val formattedMonth = month.padStart(2, '0')

        return "$formattedDay.$formattedMonth.$year"
    }
}