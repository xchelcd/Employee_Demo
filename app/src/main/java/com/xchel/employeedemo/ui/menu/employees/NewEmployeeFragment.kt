package com.xchel.employeedemo.ui.menu.employees

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xchel.employeedemo.Employee
import com.xchel.employeedemo.Location
import com.xchel.employeedemo.R
import com.xchel.employeedemo.databinding.FragmentNewEmployeeBinding
import kotlin.random.Random

class NewEmployeeFragment : Fragment() {

    private var _binding: FragmentNewEmployeeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewEmployeeBinding.inflate(layoutInflater)
        listeners()
        return binding.root
    }

    private fun listeners() {
        with(binding) {
            addButton.setOnClickListener {
                val name = binding.nameEditText.text.toString()
                val mail = binding.mailEditText.text.toString()
                val lat = binding.latEditText.text.toString()
                val log = binding.logEditText.text.toString()
                if (checkFields(name, mail, lat, log)) {
                    val location = Location(lat.toDouble(), log.toDouble())
                    val employee = Employee(-1, name, location, mail)
                    saveEmployee(employee)
                } else Toast.makeText(requireContext(), "Something was wrong", Toast.LENGTH_SHORT).show()
            }

            latRandomButton.setOnClickListener {
                latEditText.setText(getRandomNumber(90))
            }
            logRandomButton.setOnClickListener {
                logEditText.setText(getRandomNumber(180))
            }
        }
    }

    private fun saveEmployee(employee: Employee) {
        // call viewmodel to save employee
    }

    private fun checkFields(name: String, mail: String, lat: String, log: String): Boolean =
        name.isBlank() || mail.isBlank() || lat.isBlank() || log.isBlank()


    private fun getRandomNumber(nominal: Int): String =
        (-nominal + Random.nextDouble() * 2 * nominal).toString()
}