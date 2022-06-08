package com.xchel.employeedemo.ui.menu.employees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xchel.employeedemo.Employee
import com.xchel.employeedemo.R
import com.xchel.employeedemo.databinding.FragmentEmployeesBinding

class EmployeesFragment : Fragment() {

    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!

    private var list = mutableListOf<Employee>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(layoutInflater)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        val _adapter = EmployeeAdapter(list) { employeeSelected(it) }
        binding.recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun employeeSelected(employee: Employee) {
        findNavController().navigate(0)
    }

}