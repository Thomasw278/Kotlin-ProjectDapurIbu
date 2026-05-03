package com.example.dapuribuproject.fragment

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
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dapuribuproject.Adapter.KatalogAdapter
import com.example.dapuribuproject.ApiService
import com.example.dapuribuproject.DataClass.MealResponse
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Home_Fragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
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

        // Ambil Username
        val userTextView = view.findViewById<TextView>(R.id.NamaUser)
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        userTextView.text = username.replaceFirstChar { it.uppercase() }


        // Inisialisasi DB Helper | Container Populer & Favorit
        dbHelper = DatabaseHelper(requireContext())
        containerPopuler = view.findViewById(R.id.containerPopuler)
        containerFavorit = view.findViewById(R.id.containerFavorit)

        // Cek apakah database lokal kosong
        val dataLokal = dbHelper.getAllDataKatalog()

        if (dataLokal.isEmpty()) {
            // Jika kosong | Ambil Dari API
            LoadData()
        } else {
            // Jika ada | Langung Ambil DB
            AmbilDB()
        }

        // SearchBar
        val searchbar = view.findViewById<EditText>(R.id.etSearch)
        searchbar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Kosong
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Kosong
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if(query.isEmpty()){
                    AmbilDB()
                } else {
                    val listdata = dbHelper.Search(s.toString())
                    containerPopuler.removeAllViews()
                    containerFavorit.removeAllViews()
                    val marginEndPx = (16 * resources.displayMetrics.density).toInt()

                    for (katalog in listdata) {
                        val itemView = layoutInflater.inflate(R.layout.item_resep, null, false)
                        val tvFoodName = itemView.findViewById<TextView>(R.id.tvFoodName)
                        val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
                        val ivFood = itemView.findViewById<ImageView>(R.id.ivFood)
                        val btnDetail = itemView.findViewById<TextView>(R.id.tvStatus)

                        tvFoodName.text = katalog.judul_katalog
                        tvCategory.text = "Kategori: ${katalog.kategori_katalog}"

                        Glide.with(this@Home_Fragment)
                            .load(katalog.foto_katalog)
                            .placeholder(R.drawable.makanan)
                            .into(ivFood)

                        val params = LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT)
                        params.setMargins(0, 0, marginEndPx, 0)
                        itemView.layoutParams = params
                        containerPopuler.addView(itemView)

                    }
                }
            }
        })

        AmbilDB()

    }

    private fun LoadData() {
        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val daftarkategori = listOf("Beef", "Chicken", "Seafood", "Pasta", "Vegetarian", "Dutch")

        for (kategori in daftarkategori) {
            apiService.searchMeals(kategori).enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: Response<MealResponse>
                ) {
                    if (response.isSuccessful) {
                        val mealResponse = response.body()
                        val meals = mealResponse?.meals

                        for (makan in meals!!) {
                            dbHelper.insertData_Katalog(
                                makan.name ?: "",
                                makan.category ?: "",
                                makan.instructions ?: "",
                                makan.thumbnail ?: ""
                            )
                        }

                        // Update UI
                        if (isAdded) {
                            AmbilDB()
                        }
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    if (isAdded) {
                        Toast.makeText(
                            requireContext(),
                            "Gagal Mengambil Data: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    private fun AmbilDB() {
        if (!isAdded) return

        val listKatalog = dbHelper.getAllDataKatalog()
        containerPopuler.removeAllViews()
        containerFavorit.removeAllViews()
        val marginEndPx = (16 * resources.displayMetrics.density).toInt()

        for (katalog in listKatalog) {
            val itemView = layoutInflater.inflate(R.layout.item_resep, null, false)
            val tvFoodName = itemView.findViewById<TextView>(R.id.tvFoodName)
            val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
            val ivFood = itemView.findViewById<ImageView>(R.id.ivFood)
            val btnDetail = itemView.findViewById<TextView>(R.id.tvStatus)

            tvFoodName.text = katalog.judul_katalog
            tvCategory.text = "Kategori: ${katalog.kategori_katalog}"

            Glide.with(this@Home_Fragment)
                .load(katalog.foto_katalog)
                .placeholder(R.drawable.makanan)
                .into(ivFood)

            val params = LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, marginEndPx, 0)
            itemView.layoutParams = params
            containerPopuler.addView(itemView)

        }
    }
}
