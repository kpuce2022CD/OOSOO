package kr.ac.kpu.oosoosoo

import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.37.39.106/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val callContents = retrofit.create(ContentsAPI::class.java)
    val callNetflixWishes = retrofit.create(NetflixWishAPI::class.java)
    val callNetflixWatching = retrofit.create(NetflixWatchingAPI::class.java)
    val callLogin = retrofit.create(SignInAPI::class.java)
    val callSignUp = retrofit.create(SignUpAPI::class.java)
}

interface ContentsAPI {
    @GET("api/contents")
    fun getContentInfo(): Call<List<ContentInfo>>
}

interface NetflixWishAPI {
    @FormUrlEncoded
    @POST("api/n_wishes/")
    fun getNetflixWishes(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface NetflixWatchingAPI {
    @FormUrlEncoded
    @POST("api/n_watchings/")
    fun getNetflixWatching(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface SignInAPI {
    @FormUrlEncoded
    @POST("api/sign_in/")
    fun getSignIn(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface SignUpAPI {
    @FormUrlEncoded
    @POST("api/sign_up/")
    fun getSignUp(@FieldMap params: HashMap<String, String>): Call<Boolean>
}