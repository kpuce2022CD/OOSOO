package kr.ac.kpu.oosoosoo

import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class RetrofitBuilder {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.37.39.106/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val callContents = retrofit.create(ContentsAPI::class.java)
    val callNetflixWishes = retrofit.create(NetflixWishAPI::class.java)
}

interface ContentsAPI {
    @GET("api/contents")
    fun getContentInfo(): Call<List<ContentInfo>>
}

interface NetflixWishAPI {
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("api/n_wishes")
    fun getNetflixWishes(@QueryMap parameters: Map<String, String>? = null): Call<JSONObject>
}