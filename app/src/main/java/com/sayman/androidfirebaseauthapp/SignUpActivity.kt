package com.sayman.androidfirebaseauthapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button
    private var TAG: String = "SignUpActivity";
    private lateinit var auth: FirebaseAuth
    private lateinit var loadingIcon:LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnRegister = findViewById(R.id.btnRegister)

        loadingIcon = findViewById(R.id.loadingIcon)
        //Firebase Added
        auth = Firebase.auth


        btnRegister.setOnClickListener {


            loadingIcon.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(
                edtEmail.text.toString().trim(), edtPassword.text.toString().trim()
            ).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    loadingIcon.visibility = View.GONE
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        baseContext,
                        "Sign Up Successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    loadingIcon.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        }

    }

    private fun updateUI(user: FirebaseUser?) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }
}