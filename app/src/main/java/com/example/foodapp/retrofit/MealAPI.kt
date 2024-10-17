package com.example.foodapp.retrofit

import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.PopularList
import com.example.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c")categoryName: String):Call<PopularList>

    @GET("categories.php")
    fun getCategory():Call<CategoryList>

    @GET("filter.php")
    fun getCategoryDetail(@Query("c") categoryName: String):Call<PopularList>
}