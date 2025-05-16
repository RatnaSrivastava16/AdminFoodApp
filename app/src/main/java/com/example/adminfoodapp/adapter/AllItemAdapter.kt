package com.example.adminfoodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodapp.databinding.ItemBinding

class AllItemAdapter(private val MenuItemName: ArrayList<String>,private val MenuItemPrice: ArrayList<String>,private val MenuItemImage: ArrayList<Int>): RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {
    private val itemQuantites=IntArray(MenuItemName.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemViewHolder(binding)
    }

    override fun getItemCount(): Int = MenuItemName.size

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class AllItemViewHolder(private val binding: ItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = MenuItemName[position]
                foodPrice.text = MenuItemPrice[position]
                Image.setImageResource(MenuItemImage[position])
                minusbtn.setOnClickListener {
                    decreaseQuantity(position)

                }
                plusbn.setOnClickListener {
                    increaseQuantity(position)

                }
                deletebtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

            private fun decreaseQuantity(position: Int) {
                if (itemQuantites[position] > 1) {
                    itemQuantites[position]--
                    binding.cartItemQuantity.text = itemQuantites[position].toString()
                } else {
                    deleteItem(position)
                }
            }

            private fun increaseQuantity(position: Int) {
                if (itemQuantites[position] < 10) {
                    itemQuantites[position]++
                    binding.cartItemQuantity.text = itemQuantites[position].toString()

                }
            }

            private fun deleteItem(position: Int) {
                MenuItemName.removeAt(position)
                MenuItemImage.removeAt(position)
                MenuItemPrice.removeAt(position)
                notifyItemRemoved(position)
                notifyItemChanged(position, MenuItemName.size)

            }
    }
}