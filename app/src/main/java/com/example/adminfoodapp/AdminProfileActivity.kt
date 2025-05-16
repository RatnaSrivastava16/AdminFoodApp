package com.example.adminfoodapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodapp.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.NameEditText.isEnabled = false
        binding.AddressEditText.isEnabled = false
        binding.EmailEditText.isEnabled=false
        binding.PhoneEditText.isEnabled=false
        binding.PasswordEditText.isEnabled=false
        var enable=false
        binding.editProfile.setOnClickListener {
            enable=!enable
            binding.NameEditText.isEnabled = enable
            binding.AddressEditText.isEnabled = enable
            binding.EmailEditText.isEnabled=enable
            binding.PhoneEditText.isEnabled=enable
            binding.PasswordEditText.isEnabled=enable
            if(enable)
            {
                binding.NameEditText.requestFocus()
            }
        }



    }
}