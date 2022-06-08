package kr.ac.kpu.oosoosoo.contents

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content_detail.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentsReviewAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.dialog.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentDetailActivity : BaseActivity(TransitionMode.VERTICAL) {

    val call = RetrofitBuilder().callReview           // 유저가 쓴 해당 컨텐츠에 대한 리뷰 Retrofit Call
    val callReview = RetrofitBuilder().callAllReview  // 해당 컨텐츠에 대한 모든 리뷰 Retrofit Call
    val callLink = RetrofitBuilder().callOTTLink      // 요청한 OTT에서 해당 컨텐츠 시청 링크 Retrofit Call

    // ContentInfo 인텐트 받아오기
    lateinit var contentInfo : ContentInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_detail)

        contentInfo = intent.getSerializableExtra("content") as ContentInfo

        var myRating = 0.0f
        var myReview = ""
        var isExist = false
        var link = ""

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

            //구독
            var flat_data = contentInfo.flatrate.toString()
            Log.d("kim", flat_data)

            if(flat_data.contains("Disney Plus")) {
                btn_detail_disney.visibility = View.VISIBLE
            }
            if(flat_data.contains("Netflix")) {
                btn_detail_netflix.visibility = View.VISIBLE
            }
            if(flat_data.contains("wavve")) {
                btn_detail_wavve.visibility = View.VISIBLE
            }
            if (flat_data.contains("Watcha")) {
                btn_detail_watcha.visibility = View.VISIBLE
            }
            if (flat_data.contains("tving")) {
                btn_detail_tving.visibility = View.VISIBLE
            }
            if (flat_data == "") {
                layout_ott.visibility = View.GONE
                layout_ott2.visibility = View.GONE
            }

            //링크
            link = contentInfo.link.toString()

            //서버에 요청해서 유저가 남긴 해당 컨텐츠 리뷰 정보 받아오기
            var input = HashMap<String, String>()
            input["c_id"] = contentInfo.id.toString()
            input["u_email"] = Amplify.Auth.currentUser.username
            Log.d("review", "${input["c_id"]}    ${input["u_email"]}")

            call.callReview(input).enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    tv_my_rating.text = "${t.message.toString()}"
                    Log.d("review", t.message.toString())
                }
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    val body: List<String>? = response.body()
                    Log.d("review", "통신 성공")

                    if (body!!.isEmpty()) { //빈 리스트로 뜬다면
                        tv_my_rating.text = "내 평점 없음"
                    } else {
                        myRating = body[1].toFloat()
                        myReview = body[2]
                        isExist = true
                        Log.d("review", "내 평점: $myRating 내 리뷰: $myReview")

                        tv_my_rating.text = "내 평점: " + myRating.toString()
                    }
                }
            })

            callAllReview()


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
                val intent = Intent(this, RatingActivity::class.java)
                intent.putExtra("contentInfo", contentInfo)
                intent.putExtra("rating", myRating)
                intent.putExtra("review", myReview)
                intent.putExtra("isExist", isExist)
                startActivityForResult(intent, 118)
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

            // OTT 연동
            btn_detail_netflix.setOnClickListener {
                callOTTLink("netflix")
            }

            btn_detail_tving.setOnClickListener {
                callOTTLink("tving")
            }

            btn_detail_watcha.setOnClickListener {
                callOTTLink("watcha")
            }

            btn_detail_wavve.setOnClickListener {
                callOTTLink("wavve")
            }

            btn_detail_disney.setOnClickListener {
                callOTTLink("disney")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                118 -> {
                    var result_rating = data!!.getStringExtra("result_rating")
                    var rating = data!!.getFloatExtra("rating", 0.0f)
                    if(result_rating == "완료"){
                        tv_my_rating.text = "내 평점: " + rating
                    } else if(result_rating == "삭제"){
                        tv_my_rating.text = "내 평점 없음"
                    }
                    callAllReview()
                }
            }
        }
    }

    private fun callOTTLink(platform: String){
        val loadingDialog = LoadingDialog(this@ContentDetailActivity)
        val regex = "(https|http)://www(.+)".toRegex()

        loadingDialog.show()
        var input3 = HashMap<String, String>()
        input3["email"] = Amplify.Auth.currentUser.username
        input3["title"] = contentInfo.title.toString()
        input3["c_type"] = contentInfo._type.toString()
        input3["platform"] = platform

        callLink.callottlink(input3).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("detail_link", "링크 요청 실패" + t.message.toString())
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body().toString()
                Log.d("detail_link", "$body")
                if (body == null){
                    loadingDialog.dismiss()
                    Log.d("detail_link", "서버 연결 실패")
                } else {
                    Log.d("detail_link", body)
                    if (body == "interworking"){
                        loadingDialog.dismiss()
                        Log.d("detail_link", "$platform" + "에 대한 연동 정보가 없습니다.")
                    } else if (body == "contents"){
                        loadingDialog.dismiss()
                        Log.d("detail_link", "$platform" + "에 해당 컨텐츠가 없습니다.")
                    }  else {
                            loadingDialog.dismiss()
                            Log.d("detail_link", "$body")
                            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(body))
                            startActivity(intent)
                            /*else if (body.matches(regex)) {
                               loadingDialog.dismiss()
                               var intent = Intent(Intent.ACTION_VIEW, Uri.parse(body))
                               startActivity(intent)
                           } else {
                               loadingDialog.dismiss()
                               Log.d("detail_link", "url 정규표현식이 아님")
                           }*/
                    }
                }
            }
        })
    }

    private fun callAllReview() {
        // 컨텐츠에 달린 리뷰들 서버에 요청해서 모두 불러오기
        var input2 = HashMap<String, String>()
        input2["c_id"] = contentInfo.id.toString()
        callReview.allReview(input2).enqueue(object : Callback<ArrayList<ReviewInfo>>{
            override fun onFailure(call: Call<ArrayList<ReviewInfo>>, t: Throwable) {
                tv_review_title.text = "${t.message.toString()}"
                Log.d("review", "모든 리뷰 요청 실패" + t.message.toString())
            }
            override fun onResponse(call: Call<ArrayList<ReviewInfo>>, response: Response<ArrayList<ReviewInfo>>) {
                val reviewList : ArrayList<ReviewInfo> = response.body() as ArrayList<ReviewInfo>
                Log.d("review", reviewList.toString())
                if (reviewList!!.isEmpty()){
                    tv_review_title.text = "리뷰 없음"
                } else {
                    tv_review_title.text = "${contentInfo.title}에 대한 모든 리뷰"
                    //ContentsReviewAdapter(this@ContentDetailActivity, reviewList)
                    var adt = ContentsReviewAdapter(this@ContentDetailActivity,reviewList)
                    var manager01 = LinearLayoutManager(this@ContentDetailActivity,LinearLayoutManager.HORIZONTAL,false)
                    card_review_recyclerview.apply{
                        adapter = adt
                        layoutManager = manager01
                    }
                }
            }
        })
    }

}