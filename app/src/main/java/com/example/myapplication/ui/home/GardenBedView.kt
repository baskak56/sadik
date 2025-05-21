package com.example.myapplication.ui.home

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.ui.dashboard.Item
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.sqrt

class GardenBedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val gridPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER
    }

    private val conflictPaint = Paint().apply {
        color = Color.argb(100, 255, 0, 0) // полупрозрачный красный
        style = Paint.Style.FILL
    }

    // Правила совместимости (можно вынести в отдельный файл)
    private val compatibilityRules = mapOf(
        "Морковь" to setOf("Укроп", "Лук"),
        "Помидор" to setOf("Огурец", "Картофель"),
        "Картофель" to setOf("Помидор", "Тыква"),
        "Огурец" to setOf("Помидор", "Картофель")
    )

    private var plantBitmaps = mutableMapOf<String, Bitmap>()
    private var plants: List<Item> = emptyList()
    private var cellSize = 0f
    private var gridSize = 3
    private var conflictCells = mutableSetOf<Pair<Int, Int>>() // ячейки с конфликтами

    init {
        loadPlantBitmaps()
    }

    private fun loadPlantBitmaps() {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
        }

        plantBitmaps["Морковь"] = BitmapFactory.decodeResource(resources, R.drawable.defaultmorkov, options)
        plantBitmaps["Помидор"] = BitmapFactory.decodeResource(resources, R.drawable.defaultpomidor, options)
        plantBitmaps["Картофель"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkartofel, options)
        plantBitmaps["Огурец"] = BitmapFactory.decodeResource(resources, R.drawable.defaultogurec, options)
    }

    fun setPlants(plants: List<Item>) {
        this.plants = plants
        val count = plants.size
        gridSize = maxOf(3, ceil(sqrt(count.toDouble())).toInt())
        checkCompatibility()
        invalidate()
    }

    private fun checkCompatibility() {
        conflictCells.clear()

        // Проверяем все пары растений
        for (i in plants.indices) {
            for (j in i + 1 until plants.size) {
                if (arePlantsInConflict(plants[i], plants[j])) {
                    // Проверяем, являются ли растения соседями
                    if (areNeighbors(i, j)) {
                        val rowI = i / gridSize
                        val colI = i % gridSize
                        val rowJ = j / gridSize
                        val colJ = j % gridSize

                        conflictCells.add(rowI to colI)
                        conflictCells.add(rowJ to colJ)
                    }
                }
            }
        }
    }

    private fun arePlantsInConflict(plant1: Item, plant2: Item): Boolean {
        val incompatibleWithPlant1 = compatibilityRules[plant1.title] ?: return false
        val incompatibleWithPlant2 = compatibilityRules[plant2.title] ?: return false

        return plant2.title in incompatibleWithPlant1 || plant1.title in incompatibleWithPlant2
    }

    private fun areNeighbors(index1: Int, index2: Int): Boolean {
        val row1 = index1 / gridSize
        val col1 = index1 % gridSize
        val row2 = index2 / gridSize
        val col2 = index2 % gridSize

        // Проверяем соседство по горизонтали, вертикали или диагонали
        return (Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        val size = min(viewWidth, viewHeight)

        cellSize = size / gridSize

        val offsetX = (viewWidth - size) / 2
        val offsetY = (viewHeight - size) / 2

        // Рисуем белый фон
        canvas.drawRect(offsetX, offsetY, offsetX + size, offsetY + size, backgroundPaint)

        // Рисуем сетку
        for (i in 0..gridSize) {
            canvas.drawLine(
                offsetX + i * cellSize,
                offsetY,
                offsetX + i * cellSize,
                offsetY + size,
                gridPaint
            )
            canvas.drawLine(
                offsetX,
                offsetY + i * cellSize,
                offsetX + size,
                offsetY + i * cellSize,
                gridPaint
            )
        }

        // Подсвечиваем конфликтные ячейки
        for ((row, col) in conflictCells) {
            canvas.drawRect(
                offsetX + col * cellSize,
                offsetY + row * cellSize,
                offsetX + (col + 1) * cellSize,
                offsetY + (row + 1) * cellSize,
                conflictPaint
            )
        }

        // Рисуем растения
        plants.forEachIndexed { index, plant ->
            if (index < gridSize * gridSize) {
                val row = index / gridSize
                val col = index % gridSize

                val bitmap = plantBitmaps[plant.title] ?: return@forEachIndexed

                val left = offsetX + col * cellSize + cellSize * 0.1f
                val top = offsetY + row * cellSize + cellSize * 0.1f
                val right = offsetX + (col + 1) * cellSize - cellSize * 0.1f
                val bottom = offsetY + (row + 1) * cellSize - cellSize * 0.1f

                canvas.drawBitmap(bitmap, null, RectF(left, top, right, bottom), null)

                // Подпись растения
                canvas.drawText(
                    plant.title,
                    offsetX + (col + 0.5f) * cellSize,
                    offsetY + (row + 0.9f) * cellSize,
                    textPaint
                )
            }
        }
    }
}


