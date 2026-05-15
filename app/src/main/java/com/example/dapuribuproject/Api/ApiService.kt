package com.example.dapuribuproject.Api

import com.example.dapuribuproject.DataClass.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/json/v1/1/search.php")
    fun searchMeals(@Query("s") query: String): Call<MealResponse>
}