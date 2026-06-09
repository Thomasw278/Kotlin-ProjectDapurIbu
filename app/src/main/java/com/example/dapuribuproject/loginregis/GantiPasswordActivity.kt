package com.example.dapuribuproject.LoginRegis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.R
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class GantiPasswordActivity : AppCompatActivity() {

    // Inject DB Helper
    @Inject
    lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gantipw)

        //Ambil Layout - Layout Ganti Password xml
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val etKonfirmasiPassword = findViewById<EditText>(R.id.etKonfirmasiPassword)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnLogin = findViewById<Button>(R.id.btnKeLogin)

        //Cek Username dari Main Activity (Skenario Ganti PW dari user)
        val isiUsername = intent.getStringExtra("username") ?: ""
        etUsername.setText(isiUsername)

        //Ambil Data DB
        val database = db.getAllDataUser()
        var list_user = arrayListOf<String>()
        for(item in database){
            list_user.add(item.username)
        }

        // Cek Username dkk ketika button diclick
        btnSimpan.setOnClickListener {
            val getpw = db.getPassword(etUsername.text.toString())
            if(isiUsername.isNotEmpty() || (etpassword.text.toString().isNotEmpty()) || (etKonfirmasiPassword.text.toString().isNotEmpty())) {
                if (etUsername.text.toString() in list_user) {
                    if (etpassword.text.toString() != getpw) {
                        if (etpassword.text.toString() == etKonfirmasiPassword.text.toString()) {
                            if (etpassword.text.toString().length >= 8 && etKonfirmasiPassword.text.toString().length >= 8) {
                                db.UpdatePassword(etUsername.text.toString(), etpassword.text.toString())
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
                        Toast.makeText(this, "Password Baru dan Lama Sama", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Username Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Kolom Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            }
        }

        // Balik ke Login
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}