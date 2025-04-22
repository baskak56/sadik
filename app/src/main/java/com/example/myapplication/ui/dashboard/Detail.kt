package com.example.myapplication.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FragmentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем данные из Intent
        val title = intent.getStringExtra("title") ?: "Без названия"
        val detailedImageResId = intent.getIntExtra("detailedImageResId", R.drawable.ic_launcher_foreground)
        val detailedDescription = intent.getStringExtra("detailedDescription") ?: "Нет описания"

        val category = intent.getStringExtra("category") ?: ""
        val plantingPeriod = intent.getStringExtra("plantingPeriod") ?: "а"
        val plantingMethod = intent.getStringExtra("plantingMethod") ?: ""
        val depth = intent.getStringExtra("depth") ?: ""
        val spacingBetween = intent.getStringExtra("spacingBetween") ?: ""
        val rowSpacing = intent.getStringExtra("rowSpacing") ?: ""

        val watering = intent.getStringExtra("watering") ?: ""
        val lighting = intent.getStringExtra("lighting") ?: ""
        val fertilizer = intent.getStringExtra("fertilizer") ?: ""
        val temperature = intent.getStringExtra("temperature") ?: ""
        val maturity = intent.getStringExtra("maturity") ?: ""
        val harvestTips = intent.getStringExtra("harvestTips") ?: ""

        val compatibility = intent.getStringExtra("compatibility") ?: ""
        val diseases = intent.getStringExtra("diseases") ?: ""

        val seedTreatment = intent.getStringExtra("seedTreatment") ?: ""
        val germinationTime = intent.getStringExtra("germinationTime") ?: ""
        val growthFeatures = intent.getStringExtra("growthFeatures") ?: ""
        val pruning = intent.getStringExtra("pruning") ?: ""
        val commonMistakes = intent.getStringExtra("commonMistakes") ?: ""

        val soilType = intent.getStringExtra("soilType") ?: ""
        val ph = intent.getStringExtra("ph") ?: ""
        val cropRotation = intent.getStringExtra("cropRotation") ?: ""

        val regionAdvice = intent.getStringExtra("regionAdvice") ?: ""

        // Заполняем UI
        binding.detailTitle.text = title
        binding.detailImage.setImageResource(detailedImageResId)
        binding.detailInstructions.text = detailedDescription

        binding.detailCategory.text = category
        binding.detailPlantingPeriod.text = "Период посева: $plantingPeriod"
        binding.detailPlantingMethod.text = "Метод посадки: $plantingMethod"
        binding.detailDepth.text = "Глубина посадки (см): $depth"
        binding.detailSpacingBetween.text = "Расстояние между растениями (см): $spacingBetween"
        binding.detailRowSpacing.text = "Расстояние между рядами (см): $rowSpacing"

        binding.detailWatering.text = "Полив: $watering"
        binding.detailLighting.text = "Освещение: $lighting"
        binding.detailFertilizer.text = "Удобрения: $fertilizer"
        binding.detailTemperature.text = "Температура: $temperature"
        binding.detailMaturity.text = "Срок созревания: $maturity"
        binding.detailHarvestTips.text = "Рекомендации по сбору: $harvestTips"

        binding.detailCompatibility.text = "Совместимость: $compatibility"
        binding.detailDiseases.text = "Болезни и вредители: $diseases"

        binding.detailSeedTreatment.text = "Обработка семян: $seedTreatment"
        binding.detailGerminationTime.text = "Время всходов: $germinationTime"
        binding.detailGrowthFeatures.text = "Особенности роста: $growthFeatures"
        binding.detailPruning.text = "Обрезка и пасынкование: $pruning"
        binding.detailCommonMistakes.text = "Частые ошибки: $commonMistakes"

        binding.detailSoilType.text = "Тип почвы: $soilType"
        binding.detailPH.text = "Кислотность почвы: $ph"
        binding.detailCropRotation.text = "Севооборот: $cropRotation"

        binding.detailRegionAdvice.text = "Рекомендации по регионам: $regionAdvice"

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}

