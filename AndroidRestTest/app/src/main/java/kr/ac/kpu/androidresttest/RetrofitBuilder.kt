package kr.ac.kpu.androidresttest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.37.39.106/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieAPI = retrofit.create(MovieAPI::class.java)
}