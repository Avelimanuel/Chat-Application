package com.example.wechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.example.wechat.databinding.ActivityLatestMessagesBinding.inflate
import com.example.wechat.databinding.ActivityLoginBinding.inflate
import com.example.wechat.databinding.ActivityMainBinding.inflate
import com.google.firebase.auth.FirebaseAuth

class LatestMessagesActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        FirebaseAuth.getInstance()

      userLoggedInVerification()
    }

    //This function will be used to verify if the user is logged in or not.
    //When the user is already logged in,the app will not ask him/her to either login or sign_up,
    //it will go directly to the latestMessagesActivity.
    private fun userLoggedInVerification() {
        //Checking whether the user is logged in or not.
        val UID = FirebaseAuth.getInstance().uid

        if (UID != null){
            startActivity(Intent(this,LoginActivity::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

            Toast.makeText(this,"User already logged in",Toast.LENGTH_LONG).show()
        }else{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_sign_out->{
                FirebaseAuth.getInstance().signOut()
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(Intent(this,LoginActivity::class.java))
                Toast.makeText(this,"Sign out success",Toast.LENGTH_LONG).show()
            }
            R.id.menu_new_message->{

            }
      }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflating your menu
        menuInflater.inflate(R.menu.nav_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }
}