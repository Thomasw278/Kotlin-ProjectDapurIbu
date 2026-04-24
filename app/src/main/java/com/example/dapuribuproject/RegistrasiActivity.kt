package com.example.dapuribuproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dapuribuproject.Helper.DatabaseHelper

class RegistrasiActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        db = DatabaseHelper(this)

        // Ambil Text View | Button
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val ettglLahir = findViewById<EditText>(R.id.ettglLahir)
        val etRole = findViewById<EditText>(R.id.etRole)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegistrasi = findViewById<Button>(R.id.btnSimpan)
        val btnKeRegistrasi = findViewById<Button>(R.id.btnKeLogin)

        // Ambil Field DB
        var list_role = arrayOf("admin", "user")
        var daftar_user = arrayListOf<String>()
        var daftar_email = arrayListOf<String>()
        var user = db.getAllDataUser()
        for (item in user){
            daftar_user.add(item.username)
            daftar_email.add(item.email)
        }

        // Button Ke Login
        btnKeRegistrasi.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegistrasi.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val tanggal_lahir = ettglLahir.text.toString()
            val role = etRole.text.toString()
            val password = etPassword.text.toString()
            if(username.isNotEmpty() && email.isNotEmpty() && tanggal_lahir.isNotEmpty() && role.isNotEmpty() && password.isNotEmpty()){
                if(role in list_role){
                    if(username !in daftar_user){
                        if(password.length >= 8){
                            if(email !in daftar_email){
                                db.insertData_User(username, email, tanggal_lahir, role, password)
                                Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                                finish()
                                val Intent = Intent(this, LoginActivity::class.java)
                                Intent.putExtra("username", username)
                                Intent.putExtra("password", password)
                                startActivity(Intent)
                            } else {
                                Toast.makeText(this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Panjang Password Minimal 8", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Username Sudah Tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Role Tidak Valid", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}