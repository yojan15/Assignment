package com.example.assignment.fragments.productsDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.databinding.FragmentProductDetailBinding
import com.example.assignment.model.Products

class ProductDetailFragment : Fragment() {
    private lateinit var binding : FragmentProductDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the selected product information from arguments
        val product = arguments?.getParcelable<Products>("selectedProduct")

        // Display the product information in the UI
        product?.let {
            binding.titleTextView.text = it.title
            binding.priceTextView.text = "Price: $${it.price}"
            binding.descriptionTextView.text = it.description

            Glide.with(requireContext())
                .load(it.image)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.productImageView)
        }
    }
}