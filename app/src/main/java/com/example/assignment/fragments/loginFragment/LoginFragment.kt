package com.example.assignment.fragments.loginFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.api.ApiService
import com.example.assignment.databinding.FragmentLoginBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    private lateinit var apiService: ApiService
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
        apiService = createApiService()
        return binding.root
    }

    private fun createApiService(): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://103.186.133.168:8008/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = createApiService()

        val editTextPhoneNumber: EditText = binding.phoneNumber
        val buttonSubmit: Button = binding.sendOTP


        buttonSubmit.setOnClickListener {
            val userPhoneNumber = editTextPhoneNumber.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val requestBody = """
                        {
                            "phone_no": "$userPhoneNumber",
                            "clg_ref_id": " ",
                            "token": "token",
                            "grp_id": 3
                        }
                    """.trimIndent().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    val response = apiService.postData(requestBody)

                    if (response != null) {
                        val otp = response.Res_Data.OTP
                        if (otp != null) {
                            notifyUser(otp)
                            verifyOtpAndCompleteRegistration(userPhoneNumber, otp)

                            withContext(Dispatchers.Main) {
                                findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
                            }
                        } else {
                            showToast("OTP is null. Handle appropriately.")
                        }
                    }
                } catch (e: HttpException) {
                    val errorCode = e.code()
                    val errorMessage = e.message()
                    showToast("HTTP Error $errorCode: $errorMessage")

                    val errorBody = e.response()?.errorBody()?.string()
                    showToast("Error Body: $errorBody")
                } catch (e: Exception) {
                    showToast("Exception: ${e.message}")
                }
            }
        }
    }
    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun notifyUser(otp: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), "OTP: $otp", Toast.LENGTH_LONG).show()
        }
    }

    private fun verifyOtpAndCompleteRegistration(userPhoneNumber: String, otp: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val verifyRequestBody = """
                    {
                        "phone_no": "$userPhoneNumber",
                        "OTP": "$otp"
                    }
                """.trimIndent().toRequestBody()

                val verifyResponse = apiService.postData(verifyRequestBody)

              Log.e("verify","$verifyResponse")

            } catch (e: HttpException) {
                val errorCode = e.code()
                val errorMessage = e.message()
               // showToast("Verification HTTP Error $errorCode: $errorMessage")
            } catch (e: Exception) {
               showToast("Verification Exception: ${e.message}")
            }
        }
    }
}