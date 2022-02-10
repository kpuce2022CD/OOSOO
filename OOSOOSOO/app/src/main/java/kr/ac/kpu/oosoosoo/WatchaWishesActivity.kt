package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_watcha_wishes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchaWishesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watcha_wishes)

        val call = RetrofitBuilder().callWatchaWishes  //Retrofit Call

        btn_w_wish_request.setOnClickListener {

            watcha_wishes_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = watcha_wish_email_edtext.text.toString()
            input["pwd"] = watcha_wish_passwd_edtext.text.toString()
            input["name"] = watcha_wish_profile_edtext.text.toString()

            call.getWatchaWishes(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("watcha_wish", t.message.toString())
                    watcha_wishes_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    Log.d("watcha_wish", "통신 성공")
                    watcha_wishes_result_text.text = ""

                    Log.d("watcha_wish", body.toString())
                    if (body != null) {
                        watcha_wishes_result_text.text = "<${input["name"]}님의 찜목록>\n"
                        for(content in body) {
                            watcha_wishes_result_text.append(content.toString())
                            watcha_wishes_result_text.append("\n")
                        }
                    }
                }
            })

        }
        //돌아가기 버튼
        btn_w_wish_back.setOnClickListener{
            finish()
        }
    }
}
