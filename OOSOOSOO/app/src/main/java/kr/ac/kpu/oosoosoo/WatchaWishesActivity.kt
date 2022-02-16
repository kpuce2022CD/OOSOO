package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_netflix_wishes.*
import kotlinx.android.synthetic.main.activity_watcha_wishes.*
import kotlinx.android.synthetic.main.user_content_item.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchaWishesActivity : AppCompatActivity() {

    val contentList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watcha_wishes)

        val call = RetrofitBuilder().callWatchaWishes  //Retrofit Call

        //Item 어댑터 지정
        val adapter = UserContentAdapter()

        btn_w_wish_request.setOnClickListener {

            watcha_wishes_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = watcha_wish_email_edtext.text.toString()
            input["pwd"] = watcha_wish_passwd_edtext.text.toString()
            input["name"] = watcha_wish_profile_edtext.text.toString()

            //서버데이터 수신, 이후 어댑터에 데이터 저장
            call.getWatchaWishes(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d("watcha_wish", t.message.toString())
                    watcha_wishes_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body : List<String>? = response.body()
                    Log.d("watcha_wish", "통신 성공")

                    Log.d("watcha_wish", body.toString())

                    //현재 동적 뷰어가 담는 용량을 한참초과하므로 나중에 분리하여 출력하는 식으로 최적화 필요
                    contentList.add("브라더 베어")       //테스트 코드*****

                    if (body != null) {
                        watcha_wishes_result_text.text = "<${input["name"]}님의 찜목록>\n"
                        for(content in body) {
                            contentList.add(content)
                        }
                    }
                    adapter.contentList = contentList //데이터 넣어줌
                    watcha_wish_recyclerView.adapter = adapter //리사이클러뷰에 어댑터 연결
                    watcha_wish_recyclerView.layoutManager= LinearLayoutManager(this@WatchaWishesActivity)
                }
            })

        }
        //돌아가기 버튼
        btn_w_wish_back.setOnClickListener{
            finish()
        }
    }
}
