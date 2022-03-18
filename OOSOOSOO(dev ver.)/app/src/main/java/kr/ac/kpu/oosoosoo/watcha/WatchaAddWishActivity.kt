package kr.ac.kpu.oosoosoo.watcha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_watcha_add_wish.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchaAddWishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watcha_add_wish)

        val call = RetrofitBuilder().callWatchaAddWish  //Retrofit Call

        btn_w_add_wish_request.setOnClickListener {
            watcha_add_wish_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = watcha_add_wish_email_edtext.text.toString()
            input["pwd"] = watcha_add_wish_passwd_edtext.text.toString()
            input["name"] = watcha_add_wish_profile_edtext.text.toString()
            input["title"] = watcha_add_wish_title_edtext.text.toString()

            call.getWatchaAddWish(input).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("watcha_addWish", t.message.toString())
                    watcha_add_wish_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val body = response.body()
                    Log.d("watcha_addWish", "통신 성공")
                    watcha_add_wish_result_text.text = ""

                    Log.d("watcha_addWish", body.toString())
                    if (body != null) {
                        watcha_add_wish_result_text.text = body.toString()
                    }
                }
            })
        }

        //찜목록 조회 - WishesActivity
        btn_w_add_wish_move_wishlist.setOnClickListener{
            startActivity<WatchaWishesActivity>(
                "email" to watcha_add_wish_email_edtext.text.toString(),
                "pwd" to watcha_add_wish_passwd_edtext.text.toString(),
                "name" to watcha_add_wish_profile_edtext.text.toString()
            )
        }

        //돌아가기 버튼
        btn_w_add_wish_back.setOnClickListener{
            finish()
        }
    }
}