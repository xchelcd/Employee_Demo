package com.xchel.employeedemo.ui.menu.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.databinding.CellEmployeeBinding

class EmployeeAdapter(
    private val employeeList: List<Employee>,
    private val onEmployeeSelected: (Employee) -> Unit
): RecyclerView.Adapter<EmployeeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = CellEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employeeList[position]
        holder.onBind(employee) { onEmployeeSelected(employee) }
    }

    override fun getItemCount() = employeeList.size
}