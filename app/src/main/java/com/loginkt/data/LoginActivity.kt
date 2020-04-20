package com.loginkt.data

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

import com.loginkt.R

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnLogin)
        val daftar = findViewById<TextView>(R.id.tvDaftar);
        daftar.setOnClickListener(){
            intent = Intent(this, RegisterForm::class.java)
            startActivity(intent)
        }
    }


}
