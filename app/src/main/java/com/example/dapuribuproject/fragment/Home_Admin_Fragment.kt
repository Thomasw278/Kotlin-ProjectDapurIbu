package com.example.dapuribuproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dapuribuproject.R
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dapuribuproject.Helper.DatabaseHelper
import android.widget.ProgressBar
import android.widget.Toast
import com.example.dapuribuproject.Api.ApiService
import com.example.dapuribuproject.DataClass.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Home_Admin_Fragment : Fragment() {

    @Inject
    lateinit var apiService: ApiService
    private lateinit var db: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment [cite: 1]
        return inflater.inflate(R.layout.fragment_home_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil Nama
        val nama_admin = view.findViewById<TextView>(R.id.namaadmin)
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        nama_admin.text = username.replaceFirstChar { it.uppercase() }

        // DB Helper || Ambil Size DB untuk Total Resep
        db = DatabaseHelper(requireContext())
        val dataLokal = db.getAllDataKatalog()
        if (dataLokal.isEmpty()) {
            // Jika kosong | Ambil Dari API
            LoadData()
        } else {
            // Jika ada | Langung Ambil DB
            AmbilDB()
        }
    }
        private fun LoadData() {
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
                                db.insertData_Katalog(
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
                            Toast.makeText(requireContext(),"Gagal Mengambil Data: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }

        private fun AmbilDB() {
            if (!isAdded) return

            // Ambil Item Layout Tiap XML | TV Total User | Total Admin
            val container = view?.findViewById<LinearLayout>(R.id.containerStatistik)
            val total_resep = view?.findViewById<TextView>(R.id.tvTotalResep)
            val total_user = view?.findViewById<TextView>(R.id.tvTotalUser)
            val total_admin = view?.findViewById<TextView>(R.id.tvTotalAdmin)
            val total_kategori = view?.findViewById<TextView>(R.id.totalkategori)

            // Hapus Duplicate
            container?.removeAllViews()

            // Total Resep
            var panjang_data = db.getAllDataKatalog().size
            total_resep?.text = panjang_data.toString()

            // List Kategori
            var kategori = db.getAllDataKatalog()
            var nama = arrayListOf<String>()
            for (item in kategori) {
                if (item.kategori_katalog !in nama) {
                    nama.add(item.kategori_katalog)
                }
            }

            // Jumlah User | admin
            var hitung_user = 0
            var hitung_admin = 0
            for(item in db.getAllDataUser()) {
                var dummy = item.role
                if(dummy.equals("admin")){
                    hitung_admin += 1
                } else {
                    hitung_user += 1
                }
            }
            total_user?.text = hitung_user.toString()
            total_admin?.text = hitung_admin.toString()


            // Jumlah Per Kategori
            val kategori_jumlah = kategori.groupingBy { it.kategori_katalog }.eachCount()
            var jumlah_kategori = kategori_jumlah.keys.size
            total_kategori?.text = jumlah_kategori.toString()

            // Mapping Kategori Dengan Jumlah
            for (item in kategori_jumlah) {
                val barView = layoutInflater.inflate(R.layout.item_statistik_bar, container, false)
                barView.findViewById<TextView>(R.id.tvKategori).text = item.key
                barView.findViewById<TextView>(R.id.tvJumlahResep).text = item.value.toString()
                barView.findViewById<ProgressBar>(R.id.pbStatistik).progress = item.value
                container?.addView(barView)
            }
            }
        }