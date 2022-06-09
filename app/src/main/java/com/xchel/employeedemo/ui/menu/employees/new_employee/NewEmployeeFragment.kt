package com.xchel.employeedemo.ui.menu.employees.new_employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.data.model.Location
import com.xchel.employeedemo.databinding.FragmentNewEmployeeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class NewEmployeeFragment : Fragment() {

    private var _binding: FragmentNewEmployeeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewEmployeeViewModel by viewModels()

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
                if (!checkFields(name, mail, lat, log)) {
                    val location = Location(lat.toDouble(), log.toDouble())
                    val employee = Employee(0, name, location, mail, java.util.UUID.randomUUID().toString())
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
        viewModel.saveEmployee(employee)
        clearData()
        EmployeeAddedDialog(employee) { // try again
            if (it) clearData()
            else  findNavController().popBackStack()
        }.show(parentFragmentManager, "EmployeeAddedDialog")
    }

    private fun clearData() {
        with(binding) {
            nameEditText.text = null
            mailEditText.text = null
            latEditText.text = null
            logEditText.text = null
        }
    }

    private fun checkFields(name: String, mail: String, lat: String, log: String): Boolean =
        name.isBlank() || mail.isBlank() || lat.isBlank() || log.isBlank()


    private fun getRandomNumber(nominal: Int): String =
        (-nominal + Random.nextDouble() * 2 * nominal).toString()
}