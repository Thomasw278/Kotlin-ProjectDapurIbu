package com.example.dapuribuproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dapuribuproject.adapterhelper.DatabaseHelper

class AddKatalogActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var imgFoto: ImageView
    private var fotoPath: String = ""
    val Image_PICK = 100 //Kode khusus untuk buka galeri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_katalog)

        db = DatabaseHelper(this)
        imgFoto = findViewById(R.id.ivPreview)
        imgFoto.setPadding(0, 0, 0, 0)
        imgFoto.imageTintList = null

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etKategori = findViewById<EditText>(R.id.etKategori)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnFoto = findViewById<View>(R.id.btnUploadFoto)

        var hasil = db.getAllData()
        var nama = arrayListOf<String>()
        for (item in hasil) {
            nama.add(item.judul_katalog)
        }


        btnFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, Image_PICK)
        }

        btnSimpan.setOnClickListener {
            val judul = etJudul.text.toString()
            val kategori = etKategori.text.toString()
            val deskripsi = etDeskripsi.text.toString()
            val foto = if (fotoPath.isNotEmpty()) fotoPath else ""

            if (judul.isNotEmpty() && kategori.isNotEmpty() && deskripsi.isNotEmpty() && foto.isNotEmpty()) {
                if(judul in nama){
                    Toast.makeText(this, "Judul Sudah Tersedia", Toast.LENGTH_SHORT).show()
                } else {
                    db.insertData(judul, kategori, deskripsi, foto)
                    Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_PICK && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            uri?.let {
                try {
                    // Memberikan izin akses permanen ke URI ini
                    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    contentResolver.takePersistableUriPermission(it, takeFlags)

                    imgFoto.setImageURI(it)
                    fotoPath = it.toString() // Simpan URL ke Database


                } catch (e: Exception) {
                    Toast.makeText(this, "Izin akses ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}