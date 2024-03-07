package com.example.assignment.fragments.loginFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
    binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.sendOTP.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
        }

        return binding.root
    }

}