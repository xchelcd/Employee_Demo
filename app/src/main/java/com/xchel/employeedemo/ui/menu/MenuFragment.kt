package com.xchel.employeedemo.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xchel.employeedemo.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater)
        listeners()
        return binding.root
    }

    private fun listeners() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(0)
        }

        binding.partnersButton.setOnClickListener {
            findNavController().navigate(0)
        }
    }

}