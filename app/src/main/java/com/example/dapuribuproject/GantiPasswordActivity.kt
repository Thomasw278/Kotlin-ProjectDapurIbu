package com.example.dapuribuproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dapuribuproject.Helper.DatabaseHelper

class GantiPasswordActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gantipw)

        //Ambil EditView
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val etKonfirmasiPassword = findViewById<EditText>(R.id.etKonfirmasiPassword)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnLogin = findViewById<Button>(R.id.btnKeLogin)

        //Cek Username
        val isiUsername = intent.getStringExtra("username") ?: ""
        etUsername.setText(isiUsername)

        // Connect Database
        db = DatabaseHelper(this)

        val database = db.getAllDataUser()
        var list_user = arrayListOf<String>()
        for(item in database){
            list_user.add(item.username)
        }

        btnSimpan.setOnClickListener {
            if(isiUsername in list_user){
                if(etpassword.text.toString() == etKonfirmasiPassword.text.toString()){
                    if(etpassword.text.toString().length >= 8 && etKonfirmasiPassword.text.toString().length >= 8){
                        db.UpdatePassword(isiUsername, etpassword.text.toString())
                        Toast.makeText(this, "Password Berhasil Diubah", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("username", isiUsername)
                        intent.putExtra("password", etpassword.text.toString())
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Password Minimal 8 Karakter", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Password dengan Konfirmasi Tidak Sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username Tidak Ditemukan", Toast.LENGTH_SHORT).show()
            }
        }


        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}