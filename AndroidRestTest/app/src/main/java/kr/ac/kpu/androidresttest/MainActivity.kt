package kr.ac.kpu.androidresttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = RetrofitBuilder().movieAPI

        call.getMovieInfo().enqueue(object : Callback<List<MovieInfo>> {

            override fun onResponse(call: Call<List<MovieInfo>>, response: Response<List<MovieInfo>>) {
                val userInfo = response.body()

                Log.d("movie","통신 성공")

                if (userInfo != null) {
                    for (i in userInfo) {
                        Log.d("movie", i.toString())
                        movie_text.append(i.toString())
                        movie_text.append("\n")
                    }
                }
            }

            override fun onFailure(call: Call<List<MovieInfo>>, t: Throwable) {
                Log.d("movie", t.message.toString())
            }
        })
    }


}