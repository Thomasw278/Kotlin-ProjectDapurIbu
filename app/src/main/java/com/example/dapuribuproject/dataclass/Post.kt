package com.example.dapuribuproject.DataClass

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals") val meals: List<Post>?
)

data class Post(
    @SerializedName("idMeal") val id: String?,
    @SerializedName("strMeal") val name: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strMealThumb") val thumbnail: String?
)
