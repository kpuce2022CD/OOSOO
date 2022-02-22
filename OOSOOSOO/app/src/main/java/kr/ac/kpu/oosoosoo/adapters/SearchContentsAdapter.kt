package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.ContentInfo

class SearchContentsAdapter(private val context: Context, private val contentsList: ArrayList<ContentInfo>?): RecyclerView.Adapter<SearchContentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchContentsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recy_item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchContentsAdapter.ViewHolder, position: Int) {
        holder.bind(contentsList!![position], context)

        holder.itemView.setOnClickListener {
            // 상세정보 페이지로 이동
        }
    }

    override fun getItemCount(): Int {
        if (contentsList == null) {
            return 0
        } else {
            return contentsList.size
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val contentPhoto = itemView.findViewById<ImageView>(R.id.img_content_search)

        fun bind(content: ContentInfo, context: Context) {
            //content.poster_path 이용하여 사진 출력 (Glide 사용하기)
        }
    }
}