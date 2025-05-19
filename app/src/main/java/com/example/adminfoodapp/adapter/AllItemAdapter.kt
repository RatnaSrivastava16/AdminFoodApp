package com.example.adminfoodapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodapp.R
import com.example.adminfoodapp.databinding.ItemBinding
import com.example.adminfoodapp.model.AllMenu
import com.google.firebase.database.DatabaseReference

class AllItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {
    private val itemQuantites=IntArray(menuList.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemViewHolder(binding)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class AllItemViewHolder(private val binding: ItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val menuItem = menuList[position]

                foodName.text = menuItem.name ?: "No Name"
                foodPrice.text = menuItem.price ?: "0.00"

                val uriString = menuItem.imageUrl
                if (!uriString.isNullOrEmpty()) {
                    val uri = Uri.parse(uriString)
                    Glide.with(context).load(uri).into(Image)
                } else {
                    Image.setImageResource(R.drawable.food) // use your own placeholder
                }

                cartItemQuantity.text = itemQuantites[position].toString()

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
                menuList.removeAt(position)

                notifyItemRemoved(position)
                notifyItemChanged(position, menuList.size)

            }
    }
}