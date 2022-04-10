package kr.ac.kpu.oosoosoo.contents

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content_detail.*
import kotlinx.android.synthetic.main.activity_rating.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.MainActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentDetailActivity : BaseActivity(TransitionMode.VERTICAL) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_detail)

        // ContentInfo 인텐트 받아오기
        val contentInfo = intent.getSerializableExtra("content") as ContentInfo

        val call = RetrofitBuilder().callReview  //Retrofit Call

        if (contentInfo != null) {
            // Poster Image 출력
            val poster_url = "https://image.tmdb.org/t/p/original" + contentInfo.poster_path
            Glide.with(this)
                .load(poster_url)
                .placeholder(android.R.drawable.ic_popup_sync)
                .into(img_poster_detail)

            // 제목
            tv_title_detail.text = contentInfo.title

            // 날짜
            tv_date_genre.text = contentInfo.release_date + " · "

            // 장르
            val genre = contentInfo.genre?.split(",")
            if (genre != null) {
                for (i in 0..(genre.size - 3)) {
                    tv_date_genre.text = tv_date_genre.text.toString() + genre[i] + " /"
                }
                tv_date_genre.text = tv_date_genre.text.toString() + genre[genre.size - 2]
            }

            // 평점
            tv_ratingbar.text = "평균 평점<" + contentInfo.vote_average!!.toString() + ">"
            ib_rating.setColorFilter(Color.YELLOW)

            //여기에서 서버에 요청해서 review정보들 불러와서 인텐트 넘겨주고, 인텐트 넘겨받고,
            var input = HashMap<String, String>()
            input["c_id"] = contentInfo.id.toString()
            input["u_email"] = Amplify.Auth.currentUser.username
            Log.d("review", "${input["c_id"]}    ${input["u_email"]}")

            call.callReview(input).enqueue(object : Callback<List<String>> {
                //에러 발생  Expected a string but was BEGIN_ARRAY at line 1 column 3 path $[0]
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    tv_my_rating.text = "${t.message.toString()}"
                    Log.d("review", t.message.toString())
                }
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body: List<String>? = response.body()
                    Log.d("review", "통신 성공")

                    if (body!!.isEmpty()) { //빈 리스트로 뜸
                        tv_my_rating.text = "내 평점 없음"
                    } else {
                        var myRating = body[1].toFloat()
                        var myReview = body[2]
                        Log.d("review", "내 평점: $myRating 내 리뷰: $myReview")

                        tv_my_rating.text = "내 평점: " + myRating.toString()
                    }
                }
            })

            // Overview
            val overview = contentInfo.overview?.split(". ")
            if (overview != null) {
                for (i in 0..(overview.size - 2)) {
                    tv_overview_detail.text = tv_overview_detail.text.toString() + overview[i] + ".\n\n"
                }
                tv_overview_detail.text = tv_overview_detail.text.toString() + overview[overview.size - 1]
            }

            // 평가하기 기능
            ib_rating.setOnClickListener {
                startActivity<RatingActivity>(
                    "contentInfo" to contentInfo
                )
            }

            // 더보기 기능
            tv_overview_detail.post {
                val lineCount = tv_overview_detail.layout.lineCount
                if (lineCount > 0) {
                    if (tv_overview_detail.layout.getEllipsisCount(lineCount - 1) > 0) {
                        // 더보기 표시
                        tv_viewmore_overview.visibility = View.VISIBLE

                        // 더보기 클릭 이벤트
                        tv_viewmore_overview.setOnClickListener {
                            tv_overview_detail.maxLines = Int.MAX_VALUE
                            tv_viewmore_overview.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}