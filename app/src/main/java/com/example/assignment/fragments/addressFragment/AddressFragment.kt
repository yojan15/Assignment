package com.example.assignment.fragments.addressFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.apiObject.ApiManager
import com.example.assignment.databinding.FragmentAddressBinding
class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = ApiManager.apiService

        binding.add.setOnClickListener {
            val address = binding.address.text.toString()

        }
    }
}