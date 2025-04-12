package com.example.myapplication
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup

class DateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        // Получаем переданную дату
        val selectedDate = intent.getStringExtra("selectedDate")

        // Отображаем выбранную дату в TextView
        val textView: TextView = findViewById(R.id.textViewDate)
        textView.text = "Вы перешли с даты: $selectedDate"
    }
}