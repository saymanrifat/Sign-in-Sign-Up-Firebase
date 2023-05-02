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

class LoginPageActivity : AppCompatActivity() {

    private lateinit var edtLoginEmail: EditText
    private lateinit var edtLoginPassword: EditText
    private lateinit var btnLogin: Button
    private var TAG: String = "LoginPageActivity";
    private lateinit var auth: FirebaseAuth
    private lateinit var loadingIcon: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        edtLoginEmail = findViewById(R.id.edtLoginEmail)
        edtLoginPassword = findViewById(R.id.edtLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        loadingIcon = findViewById(R.id.loadingIcon)

        //Firebase Added
        auth = Firebase.auth
        //Actionbar
        btnLogin.setOnClickListener {

            loadingIcon.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(
                edtLoginEmail.text.toString().trim(),
                edtLoginPassword.text.toString().trim()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        loadingIcon.visibility = View.GONE
                        Toast.makeText(
                            baseContext,
                            "Login Successful.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        loadingIcon.visibility = View.GONE
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
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
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun toSighUpPage(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}