package com.xchel.employeedemo.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xchel.employeedemo.R
import com.xchel.employeedemo.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater)
        viewModel.fetchData()
        listeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchDataFromFirestore()
        viewModel.isLoading.observe(viewLifecycleOwner) {

        }
        viewModel.data.observe(viewLifecycleOwner) {

        }
    }

    private fun listeners() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.newEmployeeFragment)
        }

        binding.partnersButton.setOnClickListener {
            findNavController().navigate(R.id.employeesFragment)
        }

        binding.logOutButton.setOnClickListener {
            // viewModel.clearData()
            findNavController().popBackStack()
        }
    }

}