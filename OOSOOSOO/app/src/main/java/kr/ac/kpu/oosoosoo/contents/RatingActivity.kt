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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RatingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        val i_contentInfo = intent.getSerializableExtra("contentInfo") as ContentInfo

        val call = RetrofitBuilder().callRating  //Retrofit Call

        var like = 0

        var rating_point = 0.0f

        if (i_contentInfo != null) {
            tv_rating_title.text = i_contentInfo.title + "\n리뷰 남기기"

            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                rating_point = rating * 2
            }

            // 평가하기 버튼 클릭리스너
            btn_rating.setOnClickListener {
                if (rating_point != 0.0f && edt_review.text.toString() != "") {
                    val currentTime : Long = System.currentTimeMillis() + 32400000
                    val dataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                    var input = HashMap<String, String>()
                    input["c_id"] = i_contentInfo.id.toString()
                    input["u_email"] = Amplify.Auth.currentUser.username
                    input["_like"] = like.toString()
                    input["rating"] = rating_point.toString()
                    input["review"] = edt_review.text.toString()
                    input["_datetime"] = dataFormat.format(currentTime)//stringTime//LocalDateTime.now().toString()

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
                } else if (rating_point == 0.0f) {
                    tv_rating_result.text = "평점을 매겨 주세요"
                } else if (edt_review.text.toString() == ""){
                    tv_rating_result.text = "리뷰를 작성해주세요"
                }
            }
        }
    }
}