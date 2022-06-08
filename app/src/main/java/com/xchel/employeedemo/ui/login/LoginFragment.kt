package com.xchel.employeedemo.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.xchel.employeedemo.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        inits()
        listeners()
        return binding.root
    }

    private fun listeners() {
        binding.signInButton.setOnClickListener {

        }
    }

    private fun inits() {
        auth = FirebaseAuth.getInstance()
        Log.d(TAG, auth.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}