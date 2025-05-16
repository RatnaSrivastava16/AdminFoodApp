package com.example.adminfoodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodapp.adapter.AllItemAdapter
import com.example.adminfoodapp.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy{
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{
            finish()
        }
        val menuFoodName=listOf("Momo","Burger","Sandwhich")
        val menuFoodPrice=listOf("100","200","300")
        val menuFoodImage=listOf(R.drawable.food,R.drawable.food,R.drawable.food)
        val adapter= AllItemAdapter(ArrayList(menuFoodName),ArrayList(menuFoodPrice),ArrayList(menuFoodImage))
        binding.menuRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.menuRecyclerView.adapter=adapter



    }
}