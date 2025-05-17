package com.example.adminfoodapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminfoodapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Firebase Auth & Realtime Database initialization
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        // Google Sign-In Options setup
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Go to SignupActivity
        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Google Sign-In
        binding.googleBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            Log.e("LoginActivity", "Google Sign-In Intent: $signInIntent")
            launcher.launch(signInIntent)
        }

        // Email/password login
        binding.loginbtn.setOnClickListener {
            val email = binding.email.text.toString().trim().lowercase()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    Log.e("LoginActivity", "loginUser: ${task.exception?.message}")
                }
            }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.e("LoginActivity", "Google Auth Error:")

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(authTask.result?.user) // ✅ pass actual user
                        Toast.makeText(this, "Successfully signed in with Google", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
                        Log.e("LoginActivity", "Google Auth Error: ${authTask.exception?.message}")
                    }
                }
            } else {
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Google Sign-In Task Error: ${task.exception?.message}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null) {
            updateUI(currentUser)
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d("LoginActivity", "User logged in: ${user.email}")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.e("LoginActivity", "User is null in updateUI()")
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show()
        }
    }


}
