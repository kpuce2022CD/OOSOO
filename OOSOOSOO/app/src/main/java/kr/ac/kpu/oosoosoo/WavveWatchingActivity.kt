package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_wavve_watching.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WavveWatchingActivity : AppCompatActivity() {

    lateinit var loginWaySelectedItem : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wavve_watching)

        val call = RetrofitBuilder().callWavveWatching  //Retrofit Call

        val loginWayItems = arrayOf("Wavve(웨이브)","카카오톡(Kakao)","네이버(Naver)","SKT", "페이스북(Facebook)", "Apple")
        val loginWayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, loginWayItems)

        wavve_watching_login_ways.adapter = loginWayAdapter

        wavve_watching_login_ways.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                loginWaySelectedItem = when(position) {
                    0   -> "wavve"
                    1   -> "kakao"
                    2   -> "NAVER"
                    3   -> "SKT"
                    4   -> "facebook"
                    5   -> "apple"
                    else -> ""
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                loginWaySelectedItem = "wavve"
            }
        }

        btn_wav_watching_request.setOnClickListener {
            wavve_watching_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["login_way"] = loginWaySelectedItem
            input["email"] = wavve_watching_email_edtext.text.toString()
            input["pwd"] = wavve_watching_passwd_edtext.text.toString()
            input["name"] = wavve_watching_profile_edtext.text.toString()

            call.getWavveWatching(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("wavve_watching", t.message.toString())
                    wavve_watching_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    if(body.toString() == "null") {
                        wavve_watching_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                    }
                    else {
                        Log.d("wavve_watching", "통신 성공")
                        wavve_watching_result_text.text = ""

                        Log.d("wavve_watching", body.toString())
                        if (body != null) {
                            wavve_watching_result_text.text = "<${input["name"]}님의 시청중인 목록>\n"
                            for (content in body) {
                                wavve_watching_result_text.append(content)
                                wavve_watching_result_text.append("\n")
                            }
                        }
                    }
                }
            })
        }

        //돌아가기 버튼
        btn_wav_watching_back.setOnClickListener{
            finish()
        }
    }
}