package com.example.wechat

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPass: EditText
    private lateinit var btnRegister: Button
    private lateinit var loginTxt: TextView
    private lateinit var profileBtn: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        userName = findViewById(R.id.user_name)
        userEmail = findViewById(R.id.user_email)
        userPass = findViewById(R.id.user_password)
        btnRegister = findViewById(R.id.btn_register)
        loginTxt = findViewById(R.id.login_text)
        profileBtn = findViewById(R.id.profile_btn)



        btnRegister.setOnClickListener {
            createUser()

        }
        loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        profileBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }


    }


    private fun createUser() {
        var uName = userName.text.toString().trim()
        var uEmail = userEmail.text.toString().trim()
        var uPass = userPass.text.toString().trim()

        if (TextUtils.isEmpty(uName) || TextUtils.isEmpty(uEmail) || TextUtils.isEmpty(uPass)) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_LONG).show()
        } else {
            firebaseAuth.createUserWithEmailAndPassword(uEmail, uPass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show()
                            uploadImageToFirebase()
                        startActivity(Intent(this, LoginActivity::class.java))

                    } else {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show()

                    }

                }
        }
    }

    private fun uploadImageToFirebase() {

        if (selectedPhotouri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/Images/$filename")

        ref.putFile(selectedPhotouri!!)
            .addOnSuccessListener {
                Log.d("MainActivity","Photo uploaded successful: ${it.metadata?.path}")
            }

    }

    var selectedPhotouri:Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedPhotouri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotouri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            profileBtn.setBackgroundDrawable(bitmapDrawable)


        }
    }


}
