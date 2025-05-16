package com.example.adminfoodapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodapp.databinding.ActivityLoginBinding
import com.example.adminfoodapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val locationList=arrayOf("Delhi","Mumbai","Kolkata","Chennai","Bangalore")
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        binding.ListOfLocation.setAdapter(adapter)
        binding.loginbtn.setOnClickListener {
            var intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signupbtn.setOnClickListener {
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}