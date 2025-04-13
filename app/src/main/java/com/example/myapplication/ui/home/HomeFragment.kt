package com.example.myapplication.ui.home

import TaskDecorator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import android.content.Intent
import com.example.myapplication.DateActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val calendarView = binding.calendarView
        val taskDates = hashSetOf(
            CalendarDay.today(),
            CalendarDay.from(2025, 3, 10), // 10 апреля 2025
            CalendarDay.from(2025, 3, 13)  // 13 апреля 2025
        )

        calendarView.addDecorator(TaskDecorator(taskDates))

        // Установим слушатель на выбор даты
        calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                // Передаем выбранную дату в новый фрагмент или активность
                val intent = Intent(requireContext(), DateActivity::class.java)
                intent.putExtra("selectedDate", date.toString())
                startActivity(intent)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}