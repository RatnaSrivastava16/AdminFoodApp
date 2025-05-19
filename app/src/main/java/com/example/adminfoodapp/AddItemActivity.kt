package com.example.adminfoodapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminfoodapp.databinding.ActivityAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class AddItemActivity : AppCompatActivity() {
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private var foodImageUri: Uri? = null
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.addItemBtn.setOnClickListener {
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.foodDescription.text.toString().trim()
            foodIngredient = binding.foodIngredient.text.toString().trim()

            if (foodName.isNotEmpty() && foodPrice.isNotEmpty() &&
                foodDescription.isNotEmpty() && foodIngredient.isNotEmpty() &&
                foodImageUri != null
            ) {
                uploadImageToCloudinary(foodImageUri!!) { imageUrl ->
                    uploadData(imageUrl)
                }
            } else {
                Toast.makeText(this, "Please fill all the fields and select image", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            foodImageUri = uri
            binding.selectedImage.setImageURI(uri)
        }
    }

    private fun uploadImageToCloudinary(imageUri: Uri, onSuccess: (String) -> Unit) {
        val cloudName = "dtsfipp2w"
        val uploadPreset = "dolp4xiz"

        val file = File(cacheDir, "temp_image")
        val inputStream = contentResolver.openInputStream(imageUri)
        file.outputStream().use {
            inputStream?.copyTo(it)
        }

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, file.asRequestBody("image/*".toMediaTypeOrNull()))
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@AddItemActivity, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val imageUrl = Regex("\"secure_url\":\"(.*?)\"").find(responseBody!!)?.groupValues?.get(1)
                        ?.replace("\\/", "/")


                    runOnUiThread {
                        if (imageUrl != null) {
                            onSuccess(imageUrl)
                        } else {
                            Toast.makeText(this@AddItemActivity, "Failed to get image URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun uploadData(imageUrl: String) {
        val menuRef = database.getReference("menu")
        val newItemKey = menuRef.push().key

        if (newItemKey != null) {
            val foodItem = mapOf(
                "name" to foodName,
                "price" to foodPrice,
                "description" to foodDescription,
                "ingredients" to foodIngredient,
                "imageUrl" to imageUrl
            )

            menuRef.child(newItemKey).setValue(foodItem).addOnSuccessListener {
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
