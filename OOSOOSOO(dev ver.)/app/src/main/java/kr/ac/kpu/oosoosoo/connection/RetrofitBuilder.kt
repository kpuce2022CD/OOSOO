package kr.ac.kpu.oosoosoo.connection

import kr.ac.kpu.oosoosoo.contents.ContentInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.37.39.106/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val callContents = retrofit.create(ContentsAPI::class.java)
    val callNetflixWishes = retrofit.create(NetflixWishAPI::class.java)
    val callNetflixWatching = retrofit.create(NetflixWatchingAPI::class.java)
    val callNetflixAddWish = retrofit.create(NetflixAddWishAPI::class.java)
    val callWatchaWishes = retrofit.create(WatchaWishAPI::class.java)
    val callWatchaWatching = retrofit.create(WatchaWatchingAPI::class.java)
    val callWatchaAddWish = retrofit.create(WatchaAddWishAPI::class.java)
    val callWavveWishes = retrofit.create(WavveWishAPI::class.java)
    val callWavveWatching = retrofit.create(WavveWatchingAPI::class.java)
    val callLogin = retrofit.create(SignInAPI::class.java)
    val callSignUp = retrofit.create(SignUpAPI::class.java)
    val callInterworking = retrofit.create(InterworkingAPI::class.java)

    val callSearchTitle = retrofit.create(SearchTitleAPI::class.java)
    // 검색 테스트용
    val callSearchTest = retrofit.create(SearchTestAPI::class.java)

    val callUserWishList = retrofit.create(UserWishListAPI::class.java)
    val callUserWatchingLog = retrofit.create(UserWatchingLogAPI::class.java)


}

interface ContentsAPI {
    @GET("api/contents/")
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

interface NetflixAddWishAPI {
    @FormUrlEncoded
    @POST("api/n_addwish/")
    fun getNetflixAddWish(@FieldMap params: HashMap<String, String>): Call<String>
}

interface WatchaWishAPI {
    @FormUrlEncoded
    @POST("api/w_wishes/")
    fun getWatchaWishes(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface WatchaWatchingAPI {
    @FormUrlEncoded
    @POST("api/w_watchings/")
    fun getWatchaWatching(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface WatchaAddWishAPI {
    @FormUrlEncoded
    @POST("api/w_addwish/")
    fun getWatchaAddWish(@FieldMap params: HashMap<String, String>): Call<String>
}

interface WavveWishAPI {
    @FormUrlEncoded
    @POST("api/wav_wishes/")
    fun getWavveWishes(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface WavveWatchingAPI {
    @FormUrlEncoded
    @POST("api/wav_watchings/")
    fun getWavveWatching(@FieldMap params: HashMap<String, String>): Call<List<String>>
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

interface InterworkingAPI {
    @FormUrlEncoded
    @POST("api/interworking/")
    fun getInterworking(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface SearchTitleAPI {
    @FormUrlEncoded
    @POST("api/search_title/")
    fun getSearchTitle(@FieldMap params: HashMap<String, String>): Call<List<ContentInfo>>
}

//검색 테스트용
interface SearchTestAPI {
    @GET("api/search_test/")
    fun getSearchTest(): Call<List<ContentInfo>>
}

interface UserWishListAPI {
    @FormUrlEncoded
    @POST("api/user_wishlist/")
    fun getWishList(@FieldMap params: HashMap<String, String>): Call<List<ContentInfo>>
}

interface UserWatchingLogAPI {
    @FormUrlEncoded
    @POST("api/user_watchinglog/")
    fun getWatchingLog(@FieldMap params: HashMap<String, String>): Call<List<ContentInfo>>
}