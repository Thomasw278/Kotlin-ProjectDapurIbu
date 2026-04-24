package com.example.dapuribuproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dapuribuproject.Helper.DatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DatabaseHelper(this)

        //Ambil Atriubut Edit Text | Button
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnSimpan)
        val btnRegis = findViewById<Button>(R.id.btnKeRegistrasi)

        var list_user = db.getAllDataUser()

        //Ambil Username
        var daftar_username = arrayListOf<String>()
        for (item in list_user) {
            daftar_username.add(item.username)
        }

        // Ambil Data Filed Username || Password dari Registrasi || Password dari Ganti Password
        val isiUsername = intent.getStringExtra("username") ?: ""
        val isiPassword = intent.getStringExtra("password") ?: ""
        var isAdmin = false

        if (isiUsername.isNotEmpty() && isiPassword.isNotEmpty()) {
            etUsername.setText(isiUsername)
            etPassword.setText(isiPassword)
        }

        btnLogin.setOnClickListener {
            val etUsername = etUsername.text.toString()
            val etPassword = etPassword.text.toString()
            if (etUsername.isNotEmpty() && etPassword.isNotEmpty()) {
                if (etUsername in daftar_username) {
                    val password = db.getPassword(etUsername)
                    val isAdmin = db.getRoles(etUsername) == "admin"
                    if (etPassword == password) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("username", etUsername)
                        intent.putExtra("isAdmin", isAdmin)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Password Salah", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Username Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegis.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
        }
}