package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_watcha_watching.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchaWatchingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watcha_watching)

        val call = RetrofitBuilder().callWatchaWatching  //Retrofit Call

        btn_w_watching_request.setOnClickListener {
            watcha_watching_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = watcha_watching_email_edtext.text.toString()
            input["pwd"] = watcha_watching_passwd_edtext.text.toString()
            input["name"] = watcha_watching_profile_edtext.text.toString()

            call.getWatchaWatching(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("watcha_watching", t.message.toString())
                    watcha_watching_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    Log.d("watcha_watching", "통신 성공")
                    watcha_watching_result_text.text = ""

                    Log.d("watcha_watching", body.toString())
                    if (body != null) {
                        watcha_watching_result_text.text = "<${input["name"]}님의 시청중인 목록>\n"
                        for(content in body) {
                            watcha_watching_result_text.append(content)
                            watcha_watching_result_text.append("\n")
                        }
                    }
                }
            })
        }

        //돌아가기 버튼
        btn_w_watching_back.setOnClickListener{
            finish()
        }
    }
}