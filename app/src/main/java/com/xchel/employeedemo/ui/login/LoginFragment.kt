package com.xchel.employeedemo.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.xchel.employeedemo.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun listeners() {
        binding.signInButton.setOnClickListener {
            //val googleConfig = GoogleSignInOptions
            //    .Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            //    .requestIdToken(getString(R.string.web_client_id))
            //    .requestEmail()
            //    .build()

            val signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Only show accounts previously used to sign in.
                        //.setFilterByAuthorizedAccounts(true)
                        .build()
                ).build()
        }

        binding.testButton.setOnClickListener {
            findNavController().navigate(R.id.menuFragment)
        }
    }

    private fun inits() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}