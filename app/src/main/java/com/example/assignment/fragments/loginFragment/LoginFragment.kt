package com.example.assignment.fragments.loginFragment

import android.os.Bundle
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.sendOTP.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
        }       // for testing UI
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

                    response?.let {
                        it.Res_Data?.let { resData ->
                            val otp = resData.OTP
                            if (!otp.isNullOrBlank()) {
                                notifyUser(otp) // this will get the otp and send it to notify fun
                                //where i have Toast to print the otp

                                val bundle = Bundle().apply {
                                    putString("userPhoneNumber", userPhoneNumber)
                                    putString("otp", otp)
                                }

                                withContext(Dispatchers.Main) {
                                    findNavController().navigate(
                                        R.id.action_loginFragment_to_otpFragment,
                                        bundle
                                    )
                                }
                            } else {
                                showToast("OTP is null or blank. Handle appropriately.")
                            }
                        } ?: showToast("Res_Data is null. Handle appropriately.")
                    } ?: showToast("Response is null. Handle appropriately.")
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
}
