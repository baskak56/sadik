package com.example.myapplication.ui.home

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.ui.dashboard.Item
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt


class GardenBedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Базовый размер сетки (5x5 клеток)
    private val baseGridSize = 5
    private var currentColumns = baseGridSize
    private var currentRows = baseGridSize

    // Настройки грядок
    private val bedWidth = 1
    private val bedHeight = 2
    private val bedSpacing = 1

    // Цвета и стили
    private val backgroundColor = Color.WHITE
    private val gridColor = Color.LTGRAY
    private val bedColor = Color.argb(50, 139, 195, 74)
    private val conflictColor = Color.argb(100, 244, 67, 54)
    private val borderColor = Color.DKGRAY
    private val textColor = Color.BLACK

    private val backgroundPaint = Paint().apply {
        color = backgroundColor
        style = Paint.Style.FILL
    }

    private val bedPaint = Paint().apply {
        color = bedColor
        style = Paint.Style.FILL
    }

    private val gridPaint = Paint().apply {
        color = gridColor
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    private val borderPaint = Paint().apply {
        color = borderColor
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val textPaint = Paint().apply {
        color = textColor
        textSize = 24f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val conflictPaint = Paint().apply {
        color = conflictColor
        style = Paint.Style.FILL
    }

    private var plantBitmaps = mutableMapOf<String, Bitmap>()
    private var plants: List<Item> = emptyList()
    private var cellSize = 0f
    private var hasConflict = false
    private val bedLocations = mutableListOf<Rect>()
    private val conflictCells = mutableSetOf<Pair<Int, Int>>()

    init {
        loadPlantBitmaps()
    }


    private fun loadPlantBitmaps() {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
        }

        // Загрузка изображений растений
        plantBitmaps["Морковь"] = BitmapFactory.decodeResource(resources, R.drawable.defaultmorkov,options )
        plantBitmaps["Помидор"] = BitmapFactory.decodeResource(resources, R.drawable.defaultpomidor, options)
        plantBitmaps["Картофель"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkartofel, options)
        plantBitmaps["Огурец"] = BitmapFactory.decodeResource(resources, R.drawable.defaultogurec, options)
        plantBitmaps["Капуста белокочанная"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkapusta, options)
        plantBitmaps["Лук репчатый"] = BitmapFactory.decodeResource(resources, R.drawable.defaultluk, options)
        plantBitmaps["Чеснок"] = BitmapFactory.decodeResource(resources, R.drawable.defaultchesnok, options)
        plantBitmaps["Свекла"] = BitmapFactory.decodeResource(resources, R.drawable.defaultsvekla, options)
        plantBitmaps["Тыква"] = BitmapFactory.decodeResource(resources, R.drawable.defaulttykva, options)
        plantBitmaps["Кабачок"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkabachok, options)
        plantBitmaps["Перец сладкий"] = BitmapFactory.decodeResource(resources, R.drawable.defaultperec, options)
        plantBitmaps["Редис"] = BitmapFactory.decodeResource(resources, R.drawable.defaultredis, options)
        plantBitmaps["Горох"] = BitmapFactory.decodeResource(resources, R.drawable.defaultgoroh, options)
        plantBitmaps["Укроп"] = BitmapFactory.decodeResource(resources, R.drawable.defaultukrop, options)
        plantBitmaps["Петрушка"] = BitmapFactory.decodeResource(resources, R.drawable.defaultpetrushka, options)
        plantBitmaps["Сельдерей"] = BitmapFactory.decodeResource(resources, R.drawable.defaultselderey, options)
        plantBitmaps["Редька"] = BitmapFactory.decodeResource(resources, R.drawable.defaultredka, options)
        plantBitmaps["Репа"] = BitmapFactory.decodeResource(resources, R.drawable.defaultrepa, options)
        plantBitmaps["Патиссон"] = BitmapFactory.decodeResource(resources, R.drawable.defaultpatisson, options)
        plantBitmaps["Фасоль"] = BitmapFactory.decodeResource(resources, R.drawable.defaultfasol, options)
        plantBitmaps["Кукуруза"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkukuruza, options)
        plantBitmaps["Брокколи"] = BitmapFactory.decodeResource(resources, R.drawable.defaultbrokkoli, options)
        plantBitmaps["Цветная капуста"] = BitmapFactory.decodeResource(resources, R.drawable.defaultcvetnaya, options)
        plantBitmaps["Брюссельская капуста"] = BitmapFactory.decodeResource(resources, R.drawable.defaultbrussels, options)
        plantBitmaps["Кольраби"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkolrabi, options)
        plantBitmaps["Шпинат"] = BitmapFactory.decodeResource(resources, R.drawable.defaultshpinat, options)
        plantBitmaps["Щавель"] = BitmapFactory.decodeResource(resources, R.drawable.defaultshavel, options)
        plantBitmaps["Салат листовой"] = BitmapFactory.decodeResource(resources, R.drawable.defaultsalat, options)
        plantBitmaps["Руккола"] = BitmapFactory.decodeResource(resources, R.drawable.defaultrukkola, options)
        plantBitmaps["Базилик"] = BitmapFactory.decodeResource(resources, R.drawable.defaultbasil, options)
        plantBitmaps["Кинза"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkinza, options)
        plantBitmaps["Мята"] = BitmapFactory.decodeResource(resources, R.drawable.defaultmint, options)
        plantBitmaps["Тимьян"] = BitmapFactory.decodeResource(resources, R.drawable.defaultthyme, options)
        plantBitmaps["Розмарин"] = BitmapFactory.decodeResource(resources, R.drawable.defaultrosemary, options)
        plantBitmaps["Арбуз"] = BitmapFactory.decodeResource(resources, R.drawable.defaultwatermelon, options)
        plantBitmaps["Дыня"] = BitmapFactory.decodeResource(resources, R.drawable.defaultmelon, options)
        plantBitmaps["Артишок"] = BitmapFactory.decodeResource(resources, R.drawable.defaultartichoke, options)
        plantBitmaps["Спаржа"] = BitmapFactory.decodeResource(resources, R.drawable.defaultasparagus, options)
        plantBitmaps["Ревень"] = BitmapFactory.decodeResource(resources, R.drawable.defaultrhubarb, options)
        plantBitmaps["Хрен"] = BitmapFactory.decodeResource(resources, R.drawable.defaulthren, options)
        plantBitmaps["Топинамбур"] = BitmapFactory.decodeResource(resources, R.drawable.defaulttopinambur, options)
        plantBitmaps["Батат"] = BitmapFactory.decodeResource(resources, R.drawable.defaultbatat, options)
        plantBitmaps["Лук-порей"] = BitmapFactory.decodeResource(resources, R.drawable.defaultporey, options)
        plantBitmaps["Лук-шалот"] = BitmapFactory.decodeResource(resources, R.drawable.defaultshalot, options)
        plantBitmaps["Черемша"] = BitmapFactory.decodeResource(resources, R.drawable.defaultcheremsha, options)
        plantBitmaps["Кресс-салат"] = BitmapFactory.decodeResource(resources, R.drawable.defaultkress, options)
        plantBitmaps["Портулак"] = BitmapFactory.decodeResource(resources, R.drawable.defaultportulak, options)
        plantBitmaps["Мангольд"] = BitmapFactory.decodeResource(resources, R.drawable.defaultmangold, options)
        plantBitmaps["Баклажан"] = BitmapFactory.decodeResource(resources, R.drawable.defaultbaklashan, options)
    }

    fun setPlants(plants: List<Item>) {
        this.plants = plants
        arrangeBeds()
        checkCompatibility()
        requestLayout()
        invalidate()
    }

    private fun arrangeBeds() {
        bedLocations.clear()
        // Всегда начинаем с базового размера 5x5
        currentColumns = baseGridSize
        currentRows = baseGridSize

        val bedsNeeded = ceil(plants.size.toDouble() / (bedWidth * bedHeight)).toInt()
        var currentCol = 0
        var currentRow = 0

        for (i in 0 until bedsNeeded) {
            // Проверяем, помещается ли грядка в текущие размеры
            val fitsInCurrentGrid = currentCol + bedWidth <= currentColumns &&
                    currentRow + bedHeight <= currentRows

            if (!fitsInCurrentGrid) {
                // Если не помещается - расширяем сетку
                if (currentCol + bedWidth > currentColumns) {
                    currentColumns += bedWidth + bedSpacing
                }
                if (currentRow + bedHeight > currentRows) {
                    currentRows += bedHeight + bedSpacing
                }
            }

            // Добавляем грядку
            bedLocations.add(Rect(
                currentCol,
                currentRow,
                currentCol + bedWidth,
                currentRow + bedHeight
            ))

            // Переходим к следующей позиции
            currentCol += bedWidth + bedSpacing

            // Проверяем, нужно ли перейти на новую строку
            if (currentCol + bedWidth > currentColumns && i < bedsNeeded - 1) {
                currentCol = 0
                currentRow += bedHeight + bedSpacing
            }
        }
    }

    private fun checkCompatibility() {
        hasConflict = false
        conflictCells.clear()
        if (plants.size < 2) return

        val plantPositions = mutableMapOf<Pair<Int, Int>, Item>()

        plants.forEachIndexed { index, plant ->
            val bedIndex = index / (bedWidth * bedHeight)
            if (bedIndex < bedLocations.size) {
                val bed = bedLocations[bedIndex]
                val inBedPos = index % (bedWidth * bedHeight)
                val row = bed.top + (inBedPos / bedWidth)
                val col = bed.left + (inBedPos % bedWidth)
                plantPositions[row to col] = plant
            }
        }

        for ((pos1, plant1) in plantPositions) {
            for ((pos2, plant2) in plantPositions) {
                if (plant1 != plant2 && areAdjacent(pos1, pos2) && arePlantsInConflict(plant1, plant2)) {
                    hasConflict = true
                    conflictCells.add(pos1)
                    conflictCells.add(pos2)
                }
            }
        }
    }

    private fun areAdjacent(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Boolean {
        val (row1, col1) = pos1
        val (row2, col2) = pos2
        return abs(row1 - row2) <= 1 && abs(col1 - col2) <= 1
    }

    private fun arePlantsInConflict(plant1: Item, plant2: Item): Boolean {
        // Здесь должна быть ваша логика проверки совместимости растений
        // Например:
        val incompatiblePairs = mapOf(
            "Морковь" to setOf("Укроп", "Анис", "Свекла"),
            "Помидор" to setOf("Огурец", "Картофель", "Фенхель", "Кольраби"),
            "Картофель" to setOf("Помидор", "Тыква", "Огурец", "Сельдерей", "Подсолнух"),
            "Огурец" to setOf("Помидор", "Картофель", "Шалфей", "Ароматные травы"),
            "Капуста белокочанная" to setOf("Помидор", "Клубника", "Фасоль"),
            "Лук репчатый" to setOf("Горох", "Фасоль", "Шалфей"),
            "Чеснок" to setOf("Горох", "Фасоль", "Капуста"),
            "Свекла" to setOf("Горчица", "Фасоль", "Картофель"),
            "Тыква" to setOf("Картофель"),
            "Кабачок" to setOf("Картофель"),
            "Перец сладкий" to setOf("Фенхель", "Свекла"),
            "Редис" to setOf("Иссоп", "Огурцы"),
            "Горох" to setOf("Лук", "Чеснок", "Картофель"),
            "Укроп" to setOf("Морковь", "Томаты"),
            "Петрушка" to setOf("Салат", "Майоран"),
            "Сельдерей" to setOf("Картофель", "Кукуруза", "Астры"),
            "Фасоль" to setOf("Лук", "Чеснок", "Перец", "Подсолнух"),
            "Кукуруза" to setOf("Сельдерей", "Свекла"),
            "Брокколи" to setOf("Клубника", "Помидоры"),
            "Клубника" to setOf("Капуста", "Брокколи"),
            "Шпинат" to setOf("Спаржа", "Кабачки"),
            "Базилик" to setOf("Рута"),
            "Мята" to setOf("Петрушка"),
            "Арбуз" to setOf("Картофель"),
            "Дыня" to setOf("Картофель"),
            "Баклажан" to setOf("Фенхель")
        )

        return incompatiblePairs[plant1.title]?.contains(plant2.title) == true ||
                incompatiblePairs[plant2.title]?.contains(plant1.title) == true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Рассчитываем размер клетки
        cellSize = min(width.toFloat() / currentColumns, height.toFloat() / currentRows)
        val totalWidth = currentColumns * cellSize
        val totalHeight = currentRows * cellSize

        // Отрисовка фона
        canvas.drawRect(0f, 0f, totalWidth, totalHeight, backgroundPaint)

        // Отрисовка сетки
        for (i in 0..currentColumns) {
            canvas.drawLine(i * cellSize, 0f, i * cellSize, totalHeight, gridPaint)
        }
        for (i in 0..currentRows) {
            canvas.drawLine(0f, i * cellSize, totalWidth, i * cellSize, gridPaint)
        }

        // Отрисовка грядок
        for (bed in bedLocations) {
            val left = bed.left * cellSize
            val top = bed.top * cellSize
            val right = (bed.right) * cellSize
            val bottom = (bed.bottom) * cellSize

            canvas.drawRect(left, top, right, bottom, bedPaint)
            canvas.drawRect(left, top, right, bottom, gridPaint)
        }

        // Отрисовка растений
        plants.forEachIndexed { index, plant ->
            val bedIndex = index / (bedWidth * bedHeight)
            if (bedIndex < bedLocations.size) {
                val bed = bedLocations[bedIndex]
                val inBedPos = index % (bedWidth * bedHeight)
                val col = bed.left + (inBedPos % bedWidth)
                val row = bed.top + (inBedPos / bedWidth)

                plantBitmaps[plant.title]?.let { bitmap ->
                    val margin = cellSize * 0.15f
                    val left = col * cellSize + margin
                    val top = row * cellSize + margin
                    val right = (col + 1) * cellSize - margin
                    val bottom = (row + 1) * cellSize - margin

                    canvas.drawBitmap(bitmap, null, RectF(left, top, right, bottom), null)

                    // Подпись растения
                    val displayName = plant.title.takeIf { it.length <= 6 }
                        ?: "${plant.title.take(5)}..."
                    canvas.drawText(
                        displayName,
                        (col + 0.5f) * cellSize,
                        (row + 0.9f) * cellSize,
                        textPaint
                    )
                }
            }
        }

        // Подсветка конфликтов
        if (hasConflict) {
            conflictCells.forEach { (row, col) ->
                canvas.drawRect(
                    col * cellSize,
                    row * cellSize,
                    (col + 1) * cellSize,
                    (row + 1) * cellSize,
                    conflictPaint
                )
            }
        }

        // Границы всей области
        canvas.drawRect(0f, 0f, totalWidth, totalHeight, borderPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minCellSize = 50 * resources.displayMetrics.density
        val desiredWidth = (currentColumns * minCellSize).toInt()
        val desiredHeight = (currentRows * minCellSize).toInt()

        val measuredWidth = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0)
        val measuredHeight = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }
}