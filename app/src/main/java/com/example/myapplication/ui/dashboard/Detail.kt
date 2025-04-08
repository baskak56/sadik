package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout fragment_detail.xml
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        // Получаем данные из arguments
        val title = arguments?.getString("title") ?: "Без названия"
        val calories = arguments?.getInt("calories") ?: 0
        val imageResId = arguments?.getInt("imageResId") ?: R.drawable.ic_launcher_foreground
        val detailedDescription = arguments?.getString("detailedDescription") ?: "Нет описания"
        val detailedImageResId = arguments?.getInt("detailedImageResId") ?: R.drawable.ic_launcher_foreground

        // Устанавливаем данные в элементы UI
        binding.detailTitle.text = title
        binding.detailImage.setImageResource(detailedImageResId)
        binding.detailInstructions.text = detailedDescription

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()  // Возвращаемся назад
        }
        return binding.root
    }

    companion object {
        // Метод для создания нового фрагмента с передачей данных
        fun newInstance(title: String, calories: Int, imageResId: Int, detailedDescription: String, detailedImageResId: Int): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putInt("calories", calories)
            bundle.putInt("imageResId", imageResId)
            bundle.putString("detailedDescription", detailedDescription)
            bundle.putInt("detailedImageResId", detailedImageResId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
