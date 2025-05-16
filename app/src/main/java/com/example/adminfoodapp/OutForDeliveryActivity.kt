package com.example.adminfoodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodapp.adapter.DeliveryAdapter
import com.example.adminfoodapp.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{
            finish()
        }
        val customerName= arrayListOf("Ratna","Srivastav","Ratna Srivastav")
        val statusMoney= arrayListOf("Received","Not Received","Pending")
        val adapter= DeliveryAdapter(ArrayList(customerName),ArrayList(statusMoney))
        binding.deliveryRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.deliveryRecyclerView.adapter=adapter


    }
}