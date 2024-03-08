package com.example.assignment.fragments.addressFragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.assignment.R
import com.example.assignment.apiObject.ApiManager
import com.example.assignment.databinding.FragmentAddressBinding
import com.example.assignment.model.AddressRequestBody
import com.example.assignment.model.AddressListResponse
import com.example.assignment.model.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        binding.getAllAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_getAllAddresses)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "address-database"
        ).build()

        val apiService = ApiManager.apiService

        binding.add.setOnClickListener {
            val address = binding.address.text.toString()

            if (address.isNotEmpty()) {
                val request = AddressRequestBody(
                    address = address,
                    city_id = 1,
                    state_id = 1,
                    prof_zone_id = 1,
                    pincode = "456372",
                    Address_type = "Permanent"
                )

                // Check internet connectivity
                if (isOnline()) {
                    // Make Retrofit request
                    val call: Call<AddressListResponse> = apiService.addMultipleAddress(request)

                    call.enqueue(object : Callback<AddressListResponse> {
                        override fun onResponse(
                            call: Call<AddressListResponse>,
                            response: Response<AddressListResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseData = response.body()
                                // Handle the successful response data
                                // For example, show a Toast or navigate to another screen
                                showToast("Address added successfully")

                                // Mark the address as synced in Room
                                CoroutineScope(Dispatchers.IO).launch {
                                    val unsyncedAddresses =
                                        db.addressDao().getUnsyncedAddresses()
                                    for (unsyncedAddress in unsyncedAddresses) {
                                        unsyncedAddress.isSynced = true
                                        db.addressDao().insertAddress(unsyncedAddress)
                                    }
                                }
                            } else {
                                showToast("Error inputting address")
                            }
                        }

                        override fun onFailure(call: Call<AddressListResponse>, t: Throwable) {
                            Log.e("AddressFragment", "$t")
                        }
                    })
                } else {
                    // Save the address in Room database with isSynced set to false
                    CoroutineScope(Dispatchers.IO).launch {
                        val unsyncedAddress = com.example.assignment.model.AddressData(
                            id = 0, // You might need to provide a proper ID value
                            address_id = 0, // Provide a proper value for address_id
                            address = address,
                            google_address = null, // Provide a proper value if available, or set it to null
                            city_id = 1,
                            locality = null, // Provide a proper value if available, or set it to null
                            state_id = 1,
                            prof_zone_id = 1,
                            pincode = "456372",
                            Address_type = "Permanent",
                            caller_id = 0, // Provide a proper value for caller_id
                            status = 0, // Provide a proper value for status
                            patient_id = null, // Provide a proper value if available, or set it to null
                            last_modified_by = null // Provide a proper value if available, or set it to null
                        )
                        db.addressDao().insertAddress(unsyncedAddress)
                    }

                    showToast("Address added locally. Sync when online.")
                }
            } else {
                // Show an error message indicating that the address is empty
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
