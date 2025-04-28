package com.example.myapplication.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root = binding.root

        val recyclerView = binding.taskRecyclerView
        val tasks = mutableListOf(
            Task("1. Очистка участка от сорняков и мусора", false),
            Task("2. Перекопка почвы", false),
            Task("3. Определение типа почвы и её улучшение", false),
            Task("4. Разметка грядок", false),
            Task("5. Внесение удобрений", false),
            Task("6. Рыхление и выравнивание поверхности", false),
            Task("7. Полив и прогрев почвы", false),

        )

        val adapter = TaskAdapter(tasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}