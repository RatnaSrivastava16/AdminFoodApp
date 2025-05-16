package com.example.adminfoodapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.addMenu.setOnClickListener{
            val intent= Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItem.setOnClickListener{
            val intent= Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.orderDispatch.setOnClickListener{
            val intent= Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.adminProfile.setOnClickListener{
            val intent= Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.createNewUser.setOnClickListener{
            val intent= Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }

    }
}