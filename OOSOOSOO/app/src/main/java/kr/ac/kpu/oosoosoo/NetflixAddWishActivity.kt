package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_netflix_add_wish.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetflixAddWishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netflix_add_wish)

        val call = RetrofitBuilder().callNetflixAddWish  //Retrofit Call

        btn_n_add_wish_request.setOnClickListener {
            netflix_add_wish_result_text.text = "서버로부터 정보를 불러오는중.."
            var input = HashMap<String, String>()
            input["email"] = netflix_add_wish_email_edtext.text.toString()
            input["pwd"] = netflix_add_wish_passwd_edtext.text.toString()
            input["name"] = netflix_add_wish_profile_edtext.text.toString()
            input["title"] = netflix_add_wish_title_edtext.text.toString()

            call.getNetflixAddWish(input).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("netflix_addWish", t.message.toString())
                    netflix_add_wish_result_text.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val body = response.body()
                    Log.d("netflix_addWish", "통신 성공")
                    netflix_add_wish_result_text.text = ""

                    Log.d("netflix_addWish", body.toString())
                    if (body != null) {
                        netflix_add_wish_result_text.text = body.toString()
                    }
                }
            })
        }

        //찜목록 조회 - WishesActivity
        btn_n_add_wish_move_wishlist.setOnClickListener{
            startActivity<NetflixWishesActivity>(
                "email" to netflix_add_wish_email_edtext.text.toString(),
                "pwd" to netflix_add_wish_passwd_edtext.text.toString(),
                "name" to netflix_add_wish_profile_edtext.text.toString()
            )
        }

        //돌아가기 버튼
        btn_n_add_wish_back.setOnClickListener{
            finish()
        }
    }
}