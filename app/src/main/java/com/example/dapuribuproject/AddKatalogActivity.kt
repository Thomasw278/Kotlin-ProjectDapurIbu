package com.example.dapuribuproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dapuribuproject.adapterhelper.DatabaseHelper

class AddKatalogActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_katalog)

        db = DatabaseHelper(this)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etKategori = findViewById<EditText>(R.id.etKategori)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val etFoto = findViewById<EditText>(R.id.etFoto)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val judul = etJudul.text.toString()
            val kategori = etKategori.text.toString()
            val deskripsi = etDeskripsi.text.toString()
            val foto = etFoto.text.toString()

            if (judul.isNotEmpty() && kategori.isNotEmpty() && deskripsi.isNotEmpty() && foto.isNotEmpty()) {
                db.insertData(judul, kategori, deskripsi, foto)
                Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}