package com.example.assignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.model.AddressList

class AddressAdapter(private var addressList: List<AddressList>) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val pincode: TextView = itemView.findViewById(R.id.pincode)
        val zone: TextView = itemView.findViewById(R.id.zone)
        val city: TextView = itemView.findViewById(R.id.city)
        val addressId: TextView = itemView.findViewById(R.id.addressId)
        val state: TextView = itemView.findViewById(R.id.state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val addressItem = addressList[position]

        // Bind data to TextViews
        holder.textViewAddress.text = addressItem.address
        holder.pincode.text = "Pincode: ${addressItem.pincode}"
        holder.zone.text = "Zone: ${addressItem.zone}"
        holder.city.text = "City: ${addressItem.city}"
        holder.addressId.text = "Address ID: ${addressItem.address_id}"
        holder.state.text = "State: ${addressItem.state}"
    }
    override fun getItemCount(): Int {
        return addressList.size
    }

    fun updateData(newList: List<AddressList>) {
        addressList = newList
        notifyDataSetChanged()
    }
}
