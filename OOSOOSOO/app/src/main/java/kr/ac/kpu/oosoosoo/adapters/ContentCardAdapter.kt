package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recy_item_content_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.Constant.VIEW_TYPE_ITEM
import kr.ac.kpu.oosoosoo.adapters.Constant.VIEW_TYPE_LOADING
import kr.ac.kpu.oosoosoo.contents.ContentDetailActivity
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import org.jetbrains.anko.startActivity




//자식 컨테이너 어댑터
class ContentCardAdapter(context: Context, cardListData: ArrayList<ContentInfo>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //출력할 하나의 item List
    var contentList : ArrayList<ContentInfo>? = cardListData

    val context : Context = context

    //해당 컨텐츠 플랫폼 리스트
    var platformList : ArrayList<String>? = null

    /*inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
        //layout 파일에 값 출력
        fun bind(result: ContentInfo, adapter: ContentCardPlatformAdapter) {

        }
    }*/

    //출력할 xml파일 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recy_item_content_card, parent, false)
            ItemViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.recy_item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    //bind 과정
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder) {
            platformList = ArrayList(contentList!![position]!!.flatrate?.split(','))
            val contentCardPlatformAdapter = ContentCardPlatformAdapter(context, platformList!!)
            //holder.bind(contentList!![position]!!, contentCardPlatformAdapter)
            holder.itemView.card_item_title.text = contentList!![position]!!.title

            val url = "https://image.tmdb.org/t/p/original" + contentList!![position]!!.poster_path

            Glide.with(context)
                .load(url)
                .into(holder.itemView.img_card_item)

            //플랫폼 어댑터 지정(수평방향)
            holder.itemView.platform_recyclerview.layoutManager =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            holder.itemView.platform_recyclerview.adapter = contentCardPlatformAdapter

            holder.itemView.setOnClickListener {
                context.startActivity<ContentDetailActivity>(
                    "content" to contentList!![position]
                )
            }

        } else {
            Log.d("testest", contentList.toString())
        }

    }

    override fun getItemCount(): Int {
        return if (contentList == null) 0 else contentList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (contentList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) { }

}
