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

            netflix_wishes_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = netflix_wish_email_edtext.text.toString()
            input["pwd"] = netflix_wish_passwd_edtext.text.toString()
            input["name"] = netflix_wish_profile_edtext.text.toString()

            call.getNetflixWishes(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("netflix_wish", t.message.toString())
                    netflix_wishes_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    Log.d("netflix_wish", "통신 성공")
                    netflix_wishes_result_text.text = ""

                    Log.d("netflix_wish", body.toString())
                    if (body != null) {
                        netflix_wishes_result_text.text = "<${input["name"]}님의 찜목록>\n"
                        for(content in body) {
                            netflix_wishes_result_text.append(content.toString())
                            netflix_wishes_result_text.append("\n")
                        }
                    }
                }
            })

        }
        //돌아가기 버튼
        btn_n_wish_back.setOnClickListener{
            finish()
        }
    }
}