package com.example.assignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapters.AddressAdapter
import com.example.assignment.apiObject.ApiManager
import com.example.assignment.databinding.FragmentAddressBinding
import com.example.assignment.databinding.FragmentGetAllAddressesBinding
import com.example.assignment.model.AllAddress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAllAddresses : Fragment() {
    private lateinit var binding: FragmentGetAllAddressesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var addressAdapter: AddressAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGetAllAddressesBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerViewAddress
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter
        addressAdapter = AddressAdapter(emptyList())
        recyclerView.adapter = addressAdapter

        ApiManager.getAllAddress.getAllAddress().enqueue(object : Callback<AllAddress> {
            override fun onResponse(call: Call<AllAddress>, response: Response<AllAddress>) {
                if (response.isSuccessful) {
                    // Data retrieval successful, update the adapter
                    val allAddress = response.body()
                    allAddress?.let {
                        Log.d("getAddressFragment", "Data received: $it")
                        it.address_list?.let { it1 -> addressAdapter.updateData(it1) }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("getAddressFragment", "Error: ${response.code()} - ${response.message()}, Response: $errorBody")
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<AllAddress>, t: Throwable) {
                Log.e("getAddressFragment", "$t")
            }
        })

        return view
    }
}
