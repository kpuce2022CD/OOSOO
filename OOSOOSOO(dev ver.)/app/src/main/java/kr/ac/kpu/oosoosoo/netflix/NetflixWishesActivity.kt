package kr.ac.kpu.oosoosoo.netflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_netflix_wishes.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.UserContentAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetflixWishesActivity : AppCompatActivity() {

    val contentList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netflix_wishes)

        //addWish에서 연결, 데이터 전달 (22.02.15 이후 WishesAcitivity와 합칠 예정)
        if(intent.hasExtra("email") || intent.hasExtra("pwd") || intent.hasExtra("name")) {
            netflix_wish_email_edtext.setText(intent.getStringExtra("email"))
            netflix_wish_passwd_edtext.setText(intent.getStringExtra("pwd"))
            netflix_wish_profile_edtext.setText(intent.getStringExtra("name"))
        }
        val call = RetrofitBuilder().callNetflixWishes  //Retrofit Call

        //Item 어댑터 지정
        val adapter = UserContentAdapter()

        btn_n_wish_request.setOnClickListener {

            netflix_wishes_result_text.text = "서버로부터 정보를 불러오는중.."

            var input = HashMap<String, String>()
            input["email"] = netflix_wish_email_edtext.text.toString()
            input["pwd"] = netflix_wish_passwd_edtext.text.toString()
            input["name"] = netflix_wish_profile_edtext.text.toString()

            //서버데이터 수신, 이후 어댑터에 데이터 저장
            call.getNetflixWishes(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("netflix_wish", t.message.toString())
                    netflix_wishes_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    Log.d("netflix_wish", "통신 성공")

                    Log.d("netflix_wish", body.toString())
                    if (body != null) {
                        netflix_wishes_result_text.text = "<${input["name"]}님의 찜목록>\n"
                        for(content in body) {
                            contentList.add(content)
                        }
                    }
                    adapter.contentList = contentList //데이터 넣어줌
                    netflix_wish_recyclerView.adapter = adapter //리사이클러뷰에 어댑터 연결
                    netflix_wish_recyclerView.layoutManager= LinearLayoutManager(this@NetflixWishesActivity)
                }
            })

        }
        //돌아가기 버튼
        btn_n_wish_back.setOnClickListener{
            finish()
        }
    }
}