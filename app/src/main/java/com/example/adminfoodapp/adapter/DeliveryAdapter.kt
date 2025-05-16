package com.example.adminfoodapp.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodapp.R
import com.example.adminfoodapp.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val customerNames: ArrayList<String>,
    private val statusMoney: ArrayList<String>
) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                moneyStatus.text = statusMoney[position]

                val context = binding.root.context

                val colorMap = mapOf(
                    "Received" to ContextCompat.getColor(context, R.color.green),
                    "Not Received" to ContextCompat.getColor(context, R.color.red),
                    "Pending" to ContextCompat.getColor(context, R.color.grey)
                )

                val color = colorMap[statusMoney[position]] ?: ContextCompat.getColor(context, R.color.black)

                moneyStatus.setTextColor(color)
                cardStatus.backgroundTintList= ColorStateList.valueOf(color)

            }
        }
    }
}
