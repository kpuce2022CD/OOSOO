package kr.ac.kpu.oosoosoo.contents

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recy_item_review_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.Constant
import kr.ac.kpu.oosoosoo.adapters.Constant.VIEW_TYPE_ITEM
import kr.ac.kpu.oosoosoo.adapters.Constant.VIEW_TYPE_LOADING
import kr.ac.kpu.oosoosoo.adapters.ContentCardAdapter

class ContentsReviewAdapter(context: Context, cardListData: ArrayList<ReviewInfo>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //출력할 하나의 item List
    private var reviewList : ArrayList<ReviewInfo>? = cardListData

    val context : Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recy_item_review_card, parent, false)
            ItemViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recy_item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder) {
            holder.itemView.tv_review_user.text = reviewList!![position]!!.u_email + "님의 리뷰"
            holder.itemView.tv_review_text.text = reviewList!![position]!!.review
            holder.itemView.tv_review_like.text = reviewList!![position]!!._like.toString()
            holder.itemView.btn_like_click.setOnClickListener {
                //좋아요 버튼 눌렀을 때 서버에 요청해서 like 하나 늘리기
                Toast.makeText(context, "하나 늘림~!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("review", reviewList.toString())
        }
    }
    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) { }

    override fun getItemCount(): Int {
        return if (reviewList == null) 0 else reviewList!!.size
    }
    override fun getItemViewType(position: Int): Int {
        return if (reviewList!![position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }
}