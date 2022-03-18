package kr.ac.kpu.oosoosoo.netflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_netflix_watching.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetflixWatchingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netflix_watching)

        val call = RetrofitBuilder().callNetflixWatching  //Retrofit Call

        btn_n_watching_request.setOnClickListener {
            netflix_watching_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = netflix_watching_email_edtext.text.toString()
            input["pwd"] = netflix_watching_passwd_edtext.text.toString()
            input["name"] = netflix_watching_profile_edtext.text.toString()

            call.getNetflixWatching(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("netflix_watching", t.message.toString())
                    netflix_watching_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    Log.d("netflix_watching", "통신 성공")
                    netflix_watching_result_text.text = ""

                    Log.d("netflix_watching", body.toString())
                    if (body != null) {
                        netflix_watching_result_text.text = "<${input["name"]}님의 시청중인 목록>\n"
                        for(content in body) {
                            netflix_watching_result_text.append(content)
                            netflix_watching_result_text.append("\n")
                        }
                    }
                }
            })
        }

        //돌아가기 버튼
        btn_n_watching_back.setOnClickListener{
            finish()
        }
    }
}