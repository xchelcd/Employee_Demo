package com.xchel.employeedemo.ui.menu

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.xchel.employeedemo.R
import com.xchel.employeedemo.databinding.FragmentMenuBinding
import com.xchel.employeedemo.domain.isFileExists
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
        requireDownloadData()
        listeners()
        return binding.root
    }

    private fun requireDownloadData() {
        if (!isFileExists("employees_data.json")) { viewModel.fetchData() }
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
            FirebaseAuth.getInstance().signOut()
            findNavController().popBackStack()
        }
    }

}