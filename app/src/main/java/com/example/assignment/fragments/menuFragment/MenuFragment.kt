package com.example.assignment.fragments.menuFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.adapters.ProductAdapter
import com.example.assignment.apiObject.RetrofitClient
import com.example.assignment.databinding.FragmentMenuBinding
import com.example.assignment.model.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_addressFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create RecyclerView adapter and set it to the RecyclerView
        val adapter = ProductAdapter(emptyList()) { selectedProduct ->
            onProductItemClick(selectedProduct)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fetch products from the API and update the adapter
        val call: Call<List<Products>> = RetrofitClient.productApi.getProducts()
        call.enqueue(object : Callback<List<Products>> {
            override fun onResponse(call: Call<List<Products>>, response: Response<List<Products>>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    products?.let {
                        adapter.updateData(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Products>>, t: Throwable) {
                Log.e("MenuFragment", "$t")
            }
        })
    }
    private fun onProductItemClick(product: Products) {
        val bundle = Bundle().apply {
            putParcelable("selectedProduct", product)
        }
        findNavController().navigate(R.id.action_menuFragment_to_productDetailFragment, bundle)
    }
}