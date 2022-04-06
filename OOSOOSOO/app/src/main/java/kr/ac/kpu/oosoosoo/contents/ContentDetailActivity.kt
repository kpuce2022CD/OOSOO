package kr.ac.kpu.oosoosoo.contents

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content_detail.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.MainActivity
import kr.ac.kpu.oosoosoo.R
import org.jetbrains.anko.startActivity

class ContentDetailActivity : BaseActivity(TransitionMode.VERTICAL) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_detail)

        // ContentInfo 인텐트 받아오기
        val contentInfo = intent.getSerializableExtra("content") as ContentInfo

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
            tv_ratingbar.text = "평점 " + contentInfo.vote_average!!.toString()
            ib_rating.setColorFilter(Color.YELLOW)

            // 평가하기 기능
            ib_rating.setOnClickListener {
                startActivity<RatingActivity>(
                    "contentInfo" to contentInfo
                )
            }


            // Overview
            val overview = contentInfo.overview?.split(". ")
            if (overview != null) {
                for (i in 0..(overview.size - 2)) {
                    tv_overview_detail.text = tv_overview_detail.text.toString() + overview[i] + ".\n\n"
                }
                tv_overview_detail.text = tv_overview_detail.text.toString() + overview[overview.size - 1]
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