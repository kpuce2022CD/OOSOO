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
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentsReviewAdapter(context: Context, cardListData: ArrayList<ReviewInfo>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //출력할 하나의 item List
    private var reviewList : ArrayList<ReviewInfo>? = cardListData

    val context : Context = context

    val call = RetrofitBuilder().likeReview  // 해당 리뷰에 좋아요 요청 Retrofit Call

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
            var user_email = reviewList!![position]!!.u_email
            var tok_email = user_email.chunked(7)
            holder.itemView.tv_review_user.text = tok_email[0] + "****님의 리뷰"
            holder.itemView.tv_review_text.text = reviewList!![position]!!.review
            holder.itemView.tv_review_like.text = reviewList!![position]!!._like.toString()

            var islike = 1          //좋아요(1)인지 좋아요취소(0)인지 구분하기 위한 변수

            holder.itemView.btn_like_click.setOnClickListener {
                //좋아요 버튼 눌렀을 때 서버에 요청해서 _like 업데이트
                //좋아요 취소도 구현해야함
                var input = HashMap<String, String>()
                input["id"] = reviewList!![position]!!.id.toString()
                input["_like"] = reviewList!![position]!!._like.toString()
                input["islike"] = islike.toString()

                call.likeReview(input).enqueue(object: Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable){
                        Log.d("review_like", t.message.toString())
                    }
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        val body: Boolean? = response.body()
                        Log.d("review_like", "통신 성공")
                        if(body == true){
                            Toast.makeText(context, "좋아요", Toast.LENGTH_SHORT).show()
                            holder.itemView.tv_review_like.text = (reviewList!![position]!!._like + 1).toString()
                        } else {
                            Toast.makeText(context,"좋아요 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

                //Toast.makeText(context, "좋아요 취소", Toast.LENGTH_SHORT).show()
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