package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        val title = arguments?.getString("title") ?: "Без названия"
        val calories = arguments?.getInt("calories") ?: 0
        val imageResId = arguments?.getInt("imageResId") ?: R.drawable.ic_launcher_foreground
        val detailedDescription = arguments?.getString("detailedDescription") ?: "Нет описания"
        val detailedImageResId = arguments?.getInt("detailedImageResId") ?: R.drawable.ic_launcher_foreground

        binding.detailTitle.text = title
        binding.detailImage.setImageResource(detailedImageResId)
        binding.detailInstructions.text = detailedDescription

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }
}