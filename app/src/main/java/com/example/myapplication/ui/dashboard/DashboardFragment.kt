package com.example.myapplication.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenapp.ItemAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemList: List<Item>
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

        itemList = listOf(
            Item(
                R.drawable.defaultpomidor, "Томат", 20, false, "Описание томата", R.drawable.detailpomidor,
                emptyMap(), null, "Плодовый", "Апрель - Май", "Рассадой", 2, 30, 50,
                "Умеренный", "Солнечное", "Комплексные", "22-28°C", "90-100 дней", "Собирать вручную",
                "Совместим с базиликом", "Фитофтора", "Замачивание в стимуляторе", "5-10 дней", "Растет быстро",
                "Пасынкование необходимо", "Избыток влаги", "Супесчаная", "6.0-6.8", "Не сажать после пасленовых", "Для юга и средней полосы"
            ),
            Item(
                R.drawable.defaultpomidor, "Перец", 25, false, "Описание перца", R.drawable.detailpomidor,
                emptyMap(), null, "Овощ", "Март - Апрель", "Рассадой", 1, 25, 40,
                "Умеренный", "Яркий свет", "Фосфорные", "24-28°C", "100-120 дней", "Собирать зрелыми",
                "Совместим с морковью", "Тля", "Обработка фунгицидами", "7-14 дней", "Любит тепло",
                "Не требуется", "Недостаток освещения", "Суглинистая", "6.5-7.0", "После капусты", "Для юга"
            ),
            Item(
                R.drawable.defaultpomidor, "Баклажан", 30, false, "Описание баклажана", R.drawable.detailpomidor,
                emptyMap(), null, "Овощ", "Февраль - Март", "Рассадой", 1, 40, 60,
                "Частый", "Солнечное", "Азотные", "25-30°C", "110-130 дней", "Собирать по мере роста",
                "Совместим с луком", "Паутинный клещ", "Замачивание", "10-14 дней", "Чувствителен к холоду",
                "Удаление нижних листьев", "Переувлажнение", "Плодородная", "6.0-6.5", "После моркови", "Южные регионы"
            ),
            Item(
                R.drawable.defaultpomidor, "Редис", 18, false, "Описание редиса", R.drawable.detailpomidor,
                emptyMap(), null, "Корнеплод", "Апрель", "Прямым посевом", 1, 5, 10,
                "Регулярный", "Полутень", "Минеральные", "15-20°C", "20-30 дней", "Собирать до стрелкования",
                "Совместим с салатом", "Крестоцветная блошка", "Не требуется", "3-7 дней", "Быстрый рост",
                "Не требуется", "Загущение посадок", "Легкая", "6.0-7.0", "После огурцов", "Для всех регионов"
            )
            )


        adapter = ItemAdapter(itemList, { item ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("title", item.title)
                putExtra("calories", item.calories)
                putExtra("imageResId", item.imageResId)
                putExtra("detailedDescription", item.detailedDescription)
                putExtra("detailedImageResId", item.detailedImageResId)

                putExtra("category", item.category)
                putExtra("plantingPeriod", item.plantingPeriod)
                putExtra("plantingMethod", item.plantingMethod)
                putExtra("depth", item.depth)
                putExtra("spacingBetween", item.spacingBetween)
                putExtra("rowSpacing", item.rowSpacing)

                putExtra("watering", item.watering)
                putExtra("lighting", item.lighting)
                putExtra("fertilizer", item.fertilizer)
                putExtra("temperature", item.temperature)
                putExtra("maturity", item.maturity)
                putExtra("harvestTips", item.harvestTips)

                putExtra("compatibility", item.compatibility)
                putExtra("diseases", item.diseases)

                putExtra("seedTreatment", item.seedTreatment)
                putExtra("germinationTime", item.germinationTime)
                putExtra("growthFeatures", item.growthFeatures)
                putExtra("pruning", item.pruning)
                putExtra("commonMistakes", item.commonMistakes)

                putExtra("soilType", item.soilType)
                putExtra("ph", item.pH)
                putExtra("cropRotation", item.cropRotation)

                putExtra("regionAdvice", item.regionAdvice)
            }
            startActivity(intent)
        }, requireContext())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val searchListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().lowercase()
                val filtered = itemList.filter {
                    it.title.lowercase().contains(query)
                }
                adapter.updateItems(filtered)
                return true
            }
        }

        binding.searchView.setOnQueryTextListener(searchListener)

        binding.btnAll.setOnClickListener {
            adapter.updateItems(itemList)
        }

        binding.btnFavorites.setOnClickListener {
            val favorites = itemList.filter { item ->
                requireContext().getSharedPreferences("favorites", 0)
                    .getBoolean(item.title, false)
            }
            adapter.updateItems(favorites)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
