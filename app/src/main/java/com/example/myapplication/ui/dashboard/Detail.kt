package com.example.myapplication.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Используйте FragmentDetailBinding
        val binding = FragmentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем данные из Intent
        val title = intent.getStringExtra("title") ?: "Без названия"
        val calories = intent.getIntExtra("calories", 0)
        val imageResId = intent.getIntExtra("imageResId", R.drawable.ic_launcher_foreground)
        val detailedDescription = intent.getStringExtra("detailedDescription") ?: "Нет описания"
        val detailedImageResId = intent.getIntExtra("detailedImageResId", R.drawable.ic_launcher_foreground)

        // Устанавливаем данные в UI
        binding.detailTitle.text = title
        binding.detailImage.setImageResource(detailedImageResId)
        binding.detailInstructions.text = detailedDescription

        // Кнопка для возврата на предыдущий экран
        binding.btnBack.setOnClickListener {
            finish()  // Закрываем активити и возвращаемся
        }
    }
}
