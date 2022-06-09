package com.xchel.employeedemo.ui.menu.employees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.data.model.Location
import com.xchel.employeedemo.R
import com.xchel.employeedemo.databinding.FragmentEmployeesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(layoutInflater)
        listeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllEmployees()
        viewModel.employeeList.observe(viewLifecycleOwner) {
            setupView(it)
        }
    }

    private fun setupView(list: List<Employee>) {
        binding.progressBar.isVisible = false
        if (list.isEmpty()) {
            binding.infoTextView.isVisible = true
            binding.showAllEmployeesButton.isVisible = false
        } else {
            binding.showAllEmployeesButton.isVisible = true
            binding.infoTextView.isVisible = false
        }
        setupAdapter(list)
    }

    private fun listeners() {
        binding.showAllEmployeesButton.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }
    }

    private lateinit var _adapter: EmployeeAdapter
    private fun setupAdapter(list: List<Employee>) {
        _adapter = EmployeeAdapter(list) { employeeSelected(it) }
        binding.recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun employeeSelected(employee: Employee) {
        val bundle = Bundle().apply { putSerializable("employee", employee) }
        findNavController().navigate(R.id.mapFragment, bundle)
    }

}