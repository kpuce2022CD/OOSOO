package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recy_item_myreview_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.ContentDetailActivity
import kr.ac.kpu.oosoosoo.contents.ContentsReviewInfo
import kr.ac.kpu.oosoosoo.user.UserReviewActivity
import org.jetbrains.anko.startActivity

class MyReviewAdapter(context: Context, cardListData: ArrayList<ContentsReviewInfo>?) : RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {
    private var contents_review_list : ArrayList<ContentsReviewInfo>? = cardListData

    val context = context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(context: Context) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recy_item_myreview_card, parent, false)
    )

    override fun onBindViewHolder(holder: MyReviewAdapter.ViewHolder, position: Int) {
        holder.bind(context)

        holder.itemView.imageView45.setImageResource(R.drawable.ddabong)
        holder.itemView.tv_review_title3.text = contents_review_list!![position]!!.contents!!.title
        holder.itemView.tv_review_text3.text = contents_review_list!![position]!!.review!!.review
        holder.itemView.tv_review_like3.text = contents_review_list!![position]!!.review!!._like.toString()
        holder.itemView.tv_review_time2.text = contents_review_list!![position]!!.review!!._datetime

        holder.itemView.setOnClickListener{
            context.startActivity<ContentDetailActivity>(
                "content" to contents_review_list!![position]!!.contents
            )
        }
    }

    override fun getItemCount(): Int {
        return if (contents_review_list == null) 0 else contents_review_list!!.size
    }
}