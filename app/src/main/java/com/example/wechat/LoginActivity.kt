package com.example.wechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var LoginMail:EditText
    private lateinit var LoginPass:EditText

    private lateinit var LoginBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        LoginMail = findViewById(R.id.login_email)
        LoginPass = findViewById(R.id.login_password)
        LoginBtn  = findViewById(R.id.login_btn)

        LoginBtn.setOnClickListener {
            signInUser()

        }
    }

    private fun signInUser() {
        var mail = LoginMail.text.toString().trim()
        var password = LoginPass.text.toString().trim()

        firebaseAuth.signInWithEmailAndPassword(mail,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(this,"Login success",Toast.LENGTH_LONG).show()
                }

            }
            .addOnFailureListener {
                Toast.makeText(this,"Login failed",Toast.LENGTH_SHORT).show()
            }

    }




}