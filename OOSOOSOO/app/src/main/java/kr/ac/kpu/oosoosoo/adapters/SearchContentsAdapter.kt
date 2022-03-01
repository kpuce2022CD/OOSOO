package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.ContentDetailActivity
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import org.jetbrains.anko.startActivity

class SearchContentsAdapter(private val context: Context, private val contentsList: ArrayList<ContentInfo>): RecyclerView.Adapter<SearchContentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchContentsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recy_item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchContentsAdapter.ViewHolder, position: Int) {
        holder.bind(contentsList[position], context)

        holder.itemView.setOnClickListener {
            context.startActivity<ContentDetailActivity>(
                "content" to contentsList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return contentsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val contentPhoto = itemView.findViewById<ImageView>(R.id.img_content_search)
        private val contentTitle = itemView.findViewById<TextView>(R.id.tv_content_search)
        private val platformRecy = itemView.findViewById<RecyclerView>(R.id.recy_platform_search)

        fun bind(content: ContentInfo, context: Context) {
            //content.poster_path 이용하여 사진 출력 (Glide 사용)
            val url = "https://image.tmdb.org/t/p/original" + content.poster_path
            Glide.with(context)
                .load(url)
                .placeholder(android.R.drawable.ic_popup_sync)
                .into(contentPhoto)

            contentTitle.text = content.title

            //플랫폼 RecyclerView
            val platformList = ArrayList(content.flatrate?.split(","))

            platformRecy.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            platformRecy.adapter = ContentCardPlatformAdapter(context, platformList)
        }
    }
}