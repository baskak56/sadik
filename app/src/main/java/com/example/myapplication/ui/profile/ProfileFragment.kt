package com.example.myapplication.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import  android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)


        setupViews()
        loadSavedData()
        setupTextWatchers()

        return binding.root
    }


    private fun setupViews() {
        // Настройка кнопки меню
        binding.btnMenu.setOnClickListener { view ->
            showPopupMenu(view)
        }

        // Сохранение данных при изменении текста
        binding.nameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) saveUserData()
        }

        binding.surnameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) saveUserData()
        }
    }

    private fun loadSavedData() {
        binding.nameEditText.setText(sharedPreferences.getString("name", ""))
        binding.surnameEditText.setText(sharedPreferences.getString("surname", ""))
    }

    private fun saveUserData() {
        val name = binding.nameEditText.text.toString()
        val surname = binding.surnameEditText.text.toString()

        sharedPreferences.edit()
            .putString("name", name)
            .putString("surname", surname)
            .apply()
    }

    private fun showPopupMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.dropdown_menu, this.menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_item1 -> {
                        updateButton("Овощ", R.color.red)
                        viewModel.saveStatus("Овощ")
                    }

                    R.id.menu_item2 -> {
                        updateButton("Профи", R.color.blue)
                        viewModel.saveStatus("Профи")
                    }

                    R.id.menu_item3 -> {
                        updateButton("Гуру", R.color.green)
                        viewModel.saveStatus("Гуру")
                    }
                }
                true
            }
            show()
        }
    }

    private fun updateButton(text: String, colorRes: Int) {
        binding.btnMenu.text = text
        binding.btnMenu.icon = null

        val backgroundRes = when (colorRes) {
            R.color.red -> R.drawable.rounded_button_red
            R.color.blue -> R.drawable.rounded_button_blue
            R.color.green -> R.drawable.rounded_button_green
            else -> R.drawable.rounded_button_red
        }
        binding.btnMenu.setBackgroundResource(backgroundRes)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTextWatchers() {
        binding.nameEditText.doAfterTextChanged { text ->
            if (text?.length ?: 0 > 20) {
                binding.nameInputLayout.error = "Слишком длинное имя"
            } else {
                binding.nameInputLayout.error = null
            }
        }

        binding.surnameEditText.doAfterTextChanged { text ->
            if (text?.length ?: 0 > 25) {
                binding.surnameInputLayout.error = "Слишком длинная фамилия"
            } else {
                binding.surnameInputLayout.error = null
            }
        }
        binding.surnameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveUserData()
                true
            } else {
                false
            }
        }
        binding.nameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.surnameEditText.requestFocus()
                true
            } else {
                false
            }
        }   
    }
    override fun onPause() {
        super.onPause()
        saveUserData()
    }
}
