package com.example.adminfoodapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminfoodapp.databinding.ActivityLoginBinding
import com.example.adminfoodapp.databinding.ActivitySignUpBinding
import com.example.adminfoodapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var username:String
    private lateinit var nameofRestaurant:String
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth= Firebase.auth
        database=Firebase.database.reference

        val locationList=arrayOf("Delhi","Mumbai","Kolkata","Chennai","Bangalore")
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        binding.ListOfLocation.setAdapter(adapter)
        binding.loginbtn.setOnClickListener {
            var intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.createButtoon.setOnClickListener {
            email=binding.email.text.toString().trim()
            password=binding.password.text.toString().trim()
            username=binding.userName.text.toString().trim()
            nameofRestaurant=binding.restaurantName.text.toString().trim()

            if(email.isEmpty() || password.isEmpty() || username.isEmpty() || nameofRestaurant.isEmpty()){
                Toast.makeText(this,"Enter all the details",Toast.LENGTH_SHORT).show()
            }
            else {
                createAccount(email,password)

            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            if(task.isSuccessful)
            {
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                var intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("TAG", "createAccount: ${task.exception}")
            }
        }
    }

    private fun saveUserData() {
        email=binding.email.text.toString().trim()
        password=binding.password.text.toString().trim()
        username=binding.userName.text.toString().trim()
        nameofRestaurant=binding.restaurantName.text.toString().trim()
        val user= UserModel(email,password,username,nameofRestaurant)
        val userId: String=FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

    }
}