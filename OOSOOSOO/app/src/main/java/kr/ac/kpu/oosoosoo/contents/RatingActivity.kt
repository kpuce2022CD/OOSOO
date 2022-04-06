package kr.ac.kpu.oosoosoo.contents

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_rating.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import java.time.LocalDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        val i_contentInfo = intent.getSerializableExtra("contentInfo") as ContentInfo

        val call = RetrofitBuilder().callRating  //Retrofit Call

        var like = 0

        if (i_contentInfo != null) {
            tv_rating_title.text = i_contentInfo.title + " 리뷰 남기기"

            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                tv_rating2.text = "${rating * 2}"
            }

            rg_rating.setOnCheckedChangeListener { radioGroup, i ->
                when(i){
                    R.id.rg_like -> like = 1
                    R.id.rg_hate -> like = 0
                }
            }

            // 평가하기 버튼 클릭리스너
            btn_rating.setOnClickListener {
                if (tv_rating2.text != "" && edt_review.text.toString() != "") {
                    //에러나는중
                    var rating = tv_rating2.text.toString().toFloat() * 2
                    var input = HashMap<String, String>()
                    input["c_id"] = i_contentInfo.id.toString()
                    input["u_email"] = Amplify.Auth.currentUser.userId
                    input["_like"] = like.toString()
                    input["rating"] = rating.toString()
                    input["review"] = edt_review.text.toString()
                    input["_datetime"] = LocalDateTime.now().toString()
                    Log.d("rating2", "${input["c_id"]}, ${input["u_email"]}, ${input["_like"]}, ${input["rating"]}, ${input["review"]}, ${input["_datetime"]}" )

                    call.getRating(input).enqueue(object : Callback<Boolean> {
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            tv_rating_result.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                            Log.d("rating2", t.message.toString())
                        }

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            val body: Boolean? = response.body()
                            Log.d("rating2", "통신 성공")
                            if (body.toString() == "null") {
                                tv_rating_result.text = "서버 바디 = null"
                            } else {
                                if (body == true) {
                                    tv_rating_result.text = "평가 및 리뷰 완료!"
                                    finish()
                                } else {
                                    tv_rating_result.text = "평가 실패"
                                }
                            }
                        }
                    })
                } else if (tv_rating2.text == "") {
                    tv_rating_result.text = "평점을 매겨 주세요"
                } else if (edt_review.text.toString() == ""){
                    tv_rating_result.text = "리뷰를 작성해주세요"
                }
            }
        }
    }
}