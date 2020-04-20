package com.loginkt.data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.loginkt.R

class RegisterForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)

        val nama = findViewById<EditText>(R.id.nama)
        val alamat = findViewById<EditText>(R.id.alamat)
        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val daftar = findViewById<Button>(R.id.daftar)
        daftar.setOnClickListener(){
            intent = Intent(this, RegisterForm::class.java)
            startActivity(intent)
        }
    }
}
