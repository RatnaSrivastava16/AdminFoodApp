package com.example.adminfoodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodapp.databinding.PendingItemBinding

class PendingOrderAdapter(private val customerNames:ArrayList<String>,private val itemquantity:ArrayList<String>,private val foodImages:ArrayList<Int>): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding=PendingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun getItemCount(): Int =customerNames.size

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class PendingOrderViewHolder(private val binding:PendingItemBinding):RecyclerView.ViewHolder(binding.root){
        private var isAccepted=false
        fun bind(position:Int)
        {
            binding.apply{
                customerName.text=customerNames[position]
                quantity.text=itemquantity[position]
                Image.setImageResource(foodImages[position])
                acceptbtn.apply{
                    if(!isAccepted)
                    {
                        text="Accept"
                    }
                    else{
                        text="Dispatch"
                    }
                    setOnClickListener{
                        if(!isAccepted)
                        {
                            text="Dispatch"
                            isAccepted=true
                            Toast.makeText(context,"Accepted",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            customerNames.removeAt(adapterPosition)
                            itemquantity.removeAt(adapterPosition)
                            foodImages.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            Toast.makeText(context,"Dispatched",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

}