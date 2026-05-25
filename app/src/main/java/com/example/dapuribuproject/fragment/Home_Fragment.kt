package com.example.dapuribuproject.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dapuribuproject.Api.ApiService
import com.example.dapuribuproject.DataClass.MealResponse
import com.example.dapuribuproject.DataClass.Katalog
import com.example.dapuribuproject.DetailKatalogActivity
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.collections.emptyList

@AndroidEntryPoint
class Home_Fragment : Fragment() {
    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var dbHelper: DatabaseHelper
    private lateinit var containerPopuler: LinearLayout
    private lateinit var containerFavorit: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil Jam
        val jamTextView = view.findViewById<TextView>(R.id.JamTerkini)
        val jamFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val current = LocalDateTime.now().format(jamFormatter)
        jamTextView.text = "Jam Saat ini : ${current}"

        // Ambil Username | Set Up Username
        val userTextView = view.findViewById<TextView>(R.id.NamaUser)
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        userTextView.text = username.replaceFirstChar { it.uppercase() }

        // Inisialisasi Container Favorit | Populer
        containerPopuler = view.findViewById(R.id.containerPopuler)
        containerFavorit = view.findViewById(R.id.containerFavorit)

        // Ambil Data Katalog
        val dataLokal = dbHelper.getAllDataKatalog()
        if (dataLokal.isEmpty()) {
            LoadData()
        } else {
            AmbilDB()
        }

        // SearchBar
        val searchbar = view.findViewById<EditText>(R.id.etSearch)
        searchbar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if(query.isEmpty()){
                    AmbilDB()
                } else {
                    val listdata = dbHelper.Search(s.toString())
                    updateContainers(listdata, listOf())
                }
            }
        })
    }

    private fun LoadData() {
        val daftarkategori = listOf("Beef", "Chicken", "Seafood", "Pasta", "Vegetarian", "Dutch")
        for (kategori in daftarkategori) {
            apiService.searchMeals(kategori).enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    if (response.isSuccessful) {
                        val meals = response.body()?.meals
                        val listData = meals?.take(20) ?: emptyList()
                        // Tambahkan log untuk debugging data
                        Log.d("DEBUG_API", "Jumlah data yang berhasil diambil:${listData.size}")
                        meals?.forEach { makan ->
                            dbHelper.insertData_Katalog(makan.name ?: "", makan.category ?: "", makan.instructions ?: "", makan.thumbnail ?: ""
                            )
                        }
                        if (isAdded) AmbilDB()
                    }
                }
                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Error: ${t.message}", t)
                }
            })
        }
    }

    private fun AmbilDB() {
        if (!isAdded) return
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        
        val listKatalog = dbHelper.getAllDataKatalog()
        val listFavorit = dbHelper.getFavoritByUser(username)

        val listData = listKatalog?.take(20) ?: emptyList()
        // Tambahkan log untuk debugging data
        Log.d("DEBUG_API", "Jumlah data yang berhasil diambil:${listData.size}")
        
        updateContainers(listKatalog, listFavorit)
    }

    private fun updateContainers(populer: List<Katalog>, favorit: List<Katalog>) {
        containerPopuler.removeAllViews()
        containerFavorit.removeAllViews()
        val marginEndPx = (16 * resources.displayMetrics.density).toInt()
        val marginBottomPx = (12 * resources.displayMetrics.density).toInt()
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        val isAdmin = activity?.intent?.getBooleanExtra("isAdmin", false) ?: false

        // Isi Populer (Horizontal)
        populer.forEach { katalog ->
            val itemView = createItemView(katalog, username, isAdmin)
            val params = LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, marginEndPx, 0)
            itemView.layoutParams = params
            containerPopuler.addView(itemView)
        }

        // Isi Favorit (Vertical)
        favorit.forEach { katalog ->
            val itemView = createItemView(katalog, username, isAdmin)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, marginBottomPx)
            itemView.layoutParams = params
            containerFavorit.addView(itemView)
        }
    }

    private fun createItemView(katalog: Katalog, username: String, isAdmin: Boolean): View {
        val itemView = layoutInflater.inflate(R.layout.item_resep, null, false)
        val tvFoodName = itemView.findViewById<TextView>(R.id.tvFoodName)
        val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
        val ivFood = itemView.findViewById<ImageView>(R.id.ivFood)
        val btnDetail = itemView.findViewById<TextView>(R.id.tvStatus)

        tvFoodName.text = katalog.judul_katalog
        tvCategory.text = "Kategori: ${katalog.kategori_katalog}"

        Glide.with(this)
            .load(katalog.foto_katalog)
            .placeholder(R.drawable.makanan)
            .into(ivFood)

        btnDetail.setOnClickListener {
            val intent = Intent(requireContext(), DetailKatalogActivity::class.java)
            intent.putExtra("id_katalog", katalog.id_katalog)
            intent.putExtra("judul", katalog.judul_katalog)
            intent.putExtra("kategori", katalog.kategori_katalog)
            intent.putExtra("deskripsi", katalog.deskripsi_katalog)
            intent.putExtra("foto", katalog.foto_katalog)
            intent.putExtra("username", username)
            intent.putExtra("isAdmin", isAdmin)
            startActivity(intent)
        }
        return itemView
    }

    override fun onResume() {
        super.onResume()
        AmbilDB() // Refresh favorit saat kembali ke Home
    }
}
