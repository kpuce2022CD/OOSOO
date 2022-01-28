package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_netflix_wishes.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class NetflixWishesActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netflix_wishes)

        val call = RetrofitBuilder().callNetflixWishes  //Retrofit Call

        btn_n_wish_request.setOnClickListener {
            var input = HashMap<String, String>()
            input["email"] = netflix_wish_email_edtext.text.toString()
            input["pwd"] = netflix_wish_passwd_edtext.text.toString()
            input["name"] = netflix_wish_profile_edtext.text.toString()

            call.getNetflixWishes(input).enqueue(object : Callback<JSONObject> {
                override fun onResponse(
                    call: Call<JSONObject>,
                    response: Response<JSONObject>
                ) {
                    val body = response.body()
                    Log.d("netflix_wish", "통신 성공")

                    Log.d("netflix_wish", body.toString())
                    netflix_wishes_result_text.append(body.toString())
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    Log.d("netflix_wish", t.message.toString())
                }
            })

        }


        //돌아가기 버튼
        btn_n_wish_back.setOnClickListener{
            finish()
        }
    }
}