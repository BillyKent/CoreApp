package me.doapps.core.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

   private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.doapps.pe/core/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val coreApi = retrofit.create(CoreApi::class.java)

}