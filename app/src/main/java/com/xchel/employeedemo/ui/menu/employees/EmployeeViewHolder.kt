package com.xchel.employeedemo.ui.menu.employees

import androidx.recyclerview.widget.RecyclerView
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.databinding.CellEmployeeBinding

class EmployeeViewHolder(private val binding: CellEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {


    fun onBind(employee: Employee, onEmployeeSelected: () -> Unit) {
        with(binding) {
            nameTextView.text = employee.name
            emailTextView.text = employee.mail
            cellLayout.setOnClickListener {
                onEmployeeSelected()
            }
        }
    }

}