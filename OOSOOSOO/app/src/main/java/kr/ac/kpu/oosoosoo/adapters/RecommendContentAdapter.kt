package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recy_item_recommend_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.ContentDetailActivity
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import kr.ac.kpu.oosoosoo.contents.RecommendContentInfo
import org.jetbrains.anko.startActivity
import kotlin.math.round


//자식 컨테이너 어댑터
class RecommendContentAdapter(context: Context, recommendListData: ArrayList<RecommendContentInfo>?): RecyclerView.Adapter<RecommendContentAdapter.ViewHolder>() {

    //출력할 하나의 item List
    var contentList : ArrayList<RecommendContentInfo>? = recommendListData

    val context : Context = context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
        //layout 파일에 값 출력
        fun bind(result: RecommendContentInfo, context: Context, adapter: ContentCardPlatformAdapter) {
            val url = "https://image.tmdb.org/t/p/original" + result.recommend!!.poster_path

            Glide.with(context)
                .load(url)
                .centerInside()
                .override(1000,1200)
                .into(itemView.recy_item_recommend_card_imageView)

            itemView.recy_item_recommend_card_title.text = result.recommend!!.title

            val estimateRating = round(result.est_rating!! * 100) / 100
            itemView.recy_item_recommend_card_est_rating_score.text = estimateRating.toString()

            if (estimateRating != null) {
                if(estimateRating < 5.0) {
                    itemView.recy_item_recommend_card_rating_star5.setImageResource(R.drawable.ic_rating_half_star_24)
                }
                if(estimateRating == 4.0) {
                    itemView.recy_item_recommend_card_rating_star5.setImageResource(R.drawable.ic_rating_border_star_24)
                }
                if(estimateRating < 4.0) {
                    itemView.recy_item_recommend_card_rating_star5.setImageResource(R.drawable.ic_rating_border_star_24)
                    itemView.recy_item_recommend_card_rating_star4.setImageResource(R.drawable.ic_rating_half_star_24)
                }
                if(estimateRating == 3.0) {
                    itemView.recy_item_recommend_card_rating_star4.setImageResource(R.drawable.ic_rating_border_star_24)
                }
                if(estimateRating < 3.0) {
                    itemView.recy_item_recommend_card_rating_star4.setImageResource(R.drawable.ic_rating_border_star_24)
                    itemView.recy_item_recommend_card_rating_star3.setImageResource(R.drawable.ic_rating_half_star_24)
                }
                if(estimateRating == 2.0) {
                    itemView.recy_item_recommend_card_rating_star3.setImageResource(R.drawable.ic_rating_border_star_24)
                }
                if(estimateRating < 2.0) {
                    itemView.recy_item_recommend_card_rating_star3.setImageResource(R.drawable.ic_rating_border_star_24)
                    itemView.recy_item_recommend_card_rating_star2.setImageResource(R.drawable.ic_rating_half_star_24)
                }
                if(estimateRating == 1.0) {
                    itemView.recy_item_recommend_card_rating_star2.setImageResource(R.drawable.ic_rating_border_star_24)
                }
                if(estimateRating < 1.0) {
                    itemView.recy_item_recommend_card_rating_star2.setImageResource(R.drawable.ic_rating_border_star_24)
                    itemView.recy_item_recommend_card_rating_star1.setImageResource(R.drawable.ic_rating_half_star_24)
                }
                if(estimateRating == 0.0) {
                    itemView.recy_item_recommend_card_rating_star1.setImageResource(R.drawable.ic_rating_border_star_24)
                }
            }
            //플랫폼 어댑터 지정(수평방향)
            itemView.recy_item_recommend_card_platform_recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            itemView.recy_item_recommend_card_platform_recyclerview.adapter = adapter
        }
    }

    //출력할 xml파일 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendContentAdapter.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recy_item_recommend_card, parent, false)
    )


    //bind 과정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val platformList = ArrayList(contentList!![position].recommend!!.flatrate?.split(','))
        val contentCardPlatformAdapter = ContentCardPlatformAdapter(context, platformList)
        holder.bind(contentList!![position], context, contentCardPlatformAdapter)
        holder.itemView.setOnClickListener {
            context.startActivity<ContentDetailActivity>(
                "content" to contentList!![position]
            )
        }
        /* 좌우 컨텐츠 보이기 - 현재 position에 중복이 발생하여 원인 찾은 후 반영 예정
         * if(position == 0 || position == contentList!!.size - 1) { */
        holder.itemView.recy_item_recommend_card_frame.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        var mLayoutParam : RecyclerView.LayoutParams =  holder.itemView.recy_item_recommend_card_frame.layoutParams as RecyclerView.LayoutParams
        mLayoutParam.leftMargin = (screenWidth - holder.itemView.recy_item_recommend_card_frame.measuredWidthAndState)/2
        /*if(position == 0) {
            mLayoutParam.leftMargin = (screenWidth - holder.itemView.recy_item_recommend_card_frame.measuredWidthAndState)/2
        } else {
            mLayoutParam.rightMargin = (screenWidth - holder.itemView.recy_item_recommend_card_frame.measuredWidthAndState)/2
        }
    } */

    }


    override fun getItemCount(): Int {
        return contentList!!.size
    }
}
