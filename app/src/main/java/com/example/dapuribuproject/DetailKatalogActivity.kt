package com.example.dapuribuproject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailKatalogActivity : AppCompatActivity() {

    @Inject
    lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_katalog)

        // Ambil data dari Intent
        val idKatalog = intent.getIntExtra("id_katalog", -1)
        val isAdmin = intent.getBooleanExtra("isAdmin", false)
        val username = intent.getStringExtra("username") ?: "User"
        val judul = intent.getStringExtra("judul")
        val kategori = intent.getStringExtra("kategori")
        val deskripsi = intent.getStringExtra("deskripsi")
        val foto = intent.getStringExtra("foto")

        val ivDetailFoto = findViewById<ImageView>(R.id.ivDetailFoto)
        val tvDetailJudul = findViewById<TextView>(R.id.tvDetailJudul)
        val tvDetailKategori = findViewById<TextView>(R.id.tvDetailKategori)
        val tvDetailDeskripsi = findViewById<TextView>(R.id.tvDetailDeskripsi)
        val fabBack = findViewById<FloatingActionButton>(R.id.fabBack)
        val fabFavorit = findViewById<FloatingActionButton>(R.id.fabFavorit)

        // Set data ke UI
        tvDetailJudul.text = judul
        tvDetailKategori.text = kategori
        tvDetailDeskripsi.text = deskripsi
        Glide.with(this)
            .load(foto)
            .placeholder(R.drawable.makanan)
            .into(ivDetailFoto)

        fabBack.setOnClickListener {
            finish()
        }

        // Logika Favorit | Muncul jika bukan admin
        if (!isAdmin && idKatalog != -1) {
            fabFavorit.visibility = View.VISIBLE
            
            // Cek status Favorit
            var isFav = dbHelper.isFavorit(username, idKatalog)
            updateFabIcon(fabFavorit, isFav)

            fabFavorit.setOnClickListener {
                if (isFav) {
                    dbHelper.removeFavorit(username, idKatalog)
                    Toast.makeText(this, "Dihapus dari Favorit", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.addFavorit(username, idKatalog)
                    Toast.makeText(this, "Ditambahkan ke Favorit", Toast.LENGTH_SHORT).show()
                }
                isFav = !isFav
                updateFabIcon(fabFavorit, isFav)
            }
        } else {
            fabFavorit.visibility = View.GONE
        }
    }

    private fun updateFabIcon(fab: FloatingActionButton, isFav: Boolean) {
        if (isFav) {
            fab.setImageResource(R.drawable.btn_star_big_on)
        } else {
            fab.setImageResource(R.drawable.btn_star_big_off)
        }
    }
}
