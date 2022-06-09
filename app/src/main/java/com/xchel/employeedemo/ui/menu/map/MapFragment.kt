package com.xchel.employeedemo.ui.menu.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.R
import com.xchel.employeedemo.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var employee: Employee? = null

    private lateinit var googleMap: GoogleMap

    val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(layoutInflater)
        employee = arguments?.getSerializable("employee") as? Employee
        setupMap() // call on the view model with a flag
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMarker()
        viewModel.employees.observe(viewLifecycleOwner) {
            setMarkers(it)
        }
    }

    private fun setupMarker() {
        viewModel.isMapReady.observe(viewLifecycleOwner) { isMapReady ->
            if (isMapReady)
                employee?.let { employee ->
                    setMarker(employee)
                } ?: also {
                    viewModel.getAllEmployees()
                }
        }
    }

    private fun setMarker(employee: Employee) {
        googleMap.apply {
            addMarker(employee.getMarker())
            moveCamera(CameraUpdateFactory.newLatLng(employee.location.getPosition()))
        }
    }

    private fun setMarkers(employeeList: List<Employee>) {
        employeeList.forEach {
            val marker = it.getMarker()
            googleMap.addMarker(marker)
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(employeeList.first().location.getPosition()))
    }

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(p0: GoogleMap) {
        val onMarkerClick = MarkerClicked()
        googleMap = p0
        googleMap.setOnMarkerClickListener(onMarkerClick)
        viewModel.isMapReady.postValue(true)
        Log.d(TAG, p0.toString())
    }

    override fun onDestroyView() {
        viewModel.isMapReady.postValue(false)
        super.onDestroyView()
    }

}