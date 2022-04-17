package kr.ac.kpu.oosoosoo.user

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_content_detail.*
import kotlinx.android.synthetic.main.activity_content_detail.card_review_recyclerview
import kotlinx.android.synthetic.main.activity_my_review.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentsReviewAdapter
import kr.ac.kpu.oosoosoo.adapters.MyReviewAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.ContentsReviewInfo
import kr.ac.kpu.oosoosoo.contents.ReviewInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class UserReviewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)

        val call_my_review = RetrofitBuilder().myReview // 내 리뷰 불러오기

        //서버에 요청해서 유저가 남긴 해당 컨텐츠 리뷰 정보 받아오기
        var input = HashMap<String, String>()
        input["email"] = Amplify.Auth.currentUser.username
        Log.d("review_my", "${input["email"]}")

        call_my_review.myReview(input).enqueue(object : Callback<ArrayList<ContentsReviewInfo>> {
            override fun onFailure(call: Call<ArrayList<ContentsReviewInfo>>, t: Throwable) {
                tv_my_rating.text = "${t.message.toString()}"
                Log.d("review_my", t.message.toString())
            }
            override fun onResponse(call: Call<ArrayList<ContentsReviewInfo>>, response: Response<ArrayList<ContentsReviewInfo>>) {
                val reviewList : ArrayList<ContentsReviewInfo> = response.body() as ArrayList<ContentsReviewInfo>
                Log.d("review_my", "통신 성공")
                if (reviewList!!.isEmpty()){
                    Log.d("review_my", "작성한 리뷰가 없습니다.")
                } else {
                    Log.d("review_my", reviewList.toString())
                    var adt = MyReviewAdapter(this@UserReviewActivity, reviewList)
                    var manager01 = LinearLayoutManager(this@UserReviewActivity,
                        LinearLayoutManager.VERTICAL,false)
                    var rv = card_myreview_recyclerview.apply{
                        adapter = adt
                        layoutManager = manager01
                    }
                }

            }
        })


    }
}