package com.example.assignment.fragments.otpFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.api.ApiServiceForOTP
import com.example.assignment.databinding.FragmentOtpBinding
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var apiServiceForOtp: ApiServiceForOTP
    private lateinit var userPhoneNumber: String
    private lateinit var otp: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPhoneNumber = arguments?.getString("userPhoneNumber") ?: ""
        otp = arguments?.getString("otp") ?: ""

        apiServiceForOtp = createApiServiceForOTP()

        Log.d("OtpFragment", "Received userPhoneNumber: $userPhoneNumber, otp: $otp")

        binding.login.setOnClickListener {
            val enteredOtp = binding.pinview.text.toString()

            if (enteredOtp == otp) {
                showToast("OTP verification successful. Logging in...")
                navigateToMenuFragment()
            } else {
                showToast("OTP verification failed. Please enter the correct OTP.")
            }
        }
    }

    private fun createApiServiceForOTP(): ApiServiceForOTP {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://103.186.133.168:8008/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiServiceForOTP::class.java)
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToMenuFragment() {
        Log.d("Navigation", "Navigating to MenuFragment")
        findNavController().navigate(R.id.action_otpFragment_to_menuFragment)
    }
}
