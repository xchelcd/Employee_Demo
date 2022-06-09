package com.xchel.employeedemo.ui.menu.employees.new_employee

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.databinding.DialogEmployeeAddedBinding

class EmployeeAddedDialog(
    private val employee: Employee?,
    private val onAddEmployee: (Boolean) -> Unit
): DialogFragment() {

    private var _binding: DialogEmployeeAddedBinding? = null
    private val binding get() = _binding!!

    private var wasAdded: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEmployeeAddedBinding.inflate(layoutInflater)

        listeners()
        setData()

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun listeners() {
        binding.okButton.setOnClickListener {
            dismiss()
            onAddEmployee(false)
        }

        binding.newEmployeeButton.setOnClickListener {
            dismiss()
            onAddEmployee(true)
        }

        binding.closeImageButton.setOnClickListener { dismiss() }
    }

    private fun setData() {
        employee?.let {
            wasAdded = true
            binding.okButton.text = "Ok"
            binding.infoTextView.text = "The employee ${it.name} was added successfully"
        } ?: also {
            wasAdded = false
            binding.okButton.text = "Try again"
            binding.infoTextView.text = "Something was wrong.\nWould you want try again?"
        }
    }
}