

import com.example.oosoosoo.ContentInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitBuilder {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.37.39.106/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val callContents = retrofit.create(MovieAPI::class.java)
}

interface MovieAPI {

    @GET("api/contents")
    fun getContentInfo(): Call<List<ContentInfo>>
}