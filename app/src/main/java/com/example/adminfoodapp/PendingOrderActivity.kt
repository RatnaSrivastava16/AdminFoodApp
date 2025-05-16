package com.example.adminfoodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodapp.adapter.PendingOrderAdapter
import com.example.adminfoodapp.databinding.ActivityPendingOrderBinding
import com.example.adminfoodapp.databinding.PendingItemBinding
import java.util.ArrayList

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy{
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{
            finish()
        }
        val customerName=arrayListOf( "Ratna","Srivastava","Ratna Srivastava" )
        val money=arrayListOf("5","8","10")
        val image=arrayListOf(R.drawable.food,R.drawable.food,R.drawable.food)
        val adapter= PendingOrderAdapter(ArrayList(customerName),ArrayList(money),ArrayList(image))
        binding.PendingOrderRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.PendingOrderRecyclerView.adapter=adapter




    }
}