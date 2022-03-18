package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recy_item_content_card.view.*
import kotlinx.android.synthetic.main.recy_item_content_row.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.ContentDetailActivity
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import org.jetbrains.anko.startActivity

//자식 컨테이너 어댑터
class ContentCardAdapter(context: Context, cardListData: ArrayList<ContentInfo>?): RecyclerView.Adapter<ContentCardAdapter.ViewHolder>() {

    //출력할 하나의 item List
    private var contentList : ArrayList<ContentInfo>? = cardListData

    val context : Context = context

    //해당 컨텐츠 플랫폼 리스트
    var platformList : ArrayList<String>? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
        //layout 파일에 값 출력
        fun bind(result: ContentInfo, context: Context, adapter: ContentCardPlatformAdapter) {
            itemView.card_item_title.text = result.title

            val url = "https://image.tmdb.org/t/p/original" + contentList!![position].poster_path

            Glide.with(context)
                .load(url)
                .into(itemView.img_card_item)

            //플랫폼 어댑터 지정(수평방향)
            itemView.platform_recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            itemView.platform_recyclerview.adapter = adapter
        }
    }

    //출력할 xml파일 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recy_item_content_card, parent,
            false
        )
    )

    //bind 과정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val platformList = ArrayList(contentList!![position].flatrate?.split(','))
        val contentCardPlatformAdapter = ContentCardPlatformAdapter(context, platformList)
        holder.bind(contentList!![position], context, contentCardPlatformAdapter)
        holder.itemView.setOnClickListener {
            context.startActivity<ContentDetailActivity>(
                "content" to contentList!![position]
            )
        }

    }

    override fun getItemCount(): Int = contentList!!.size

}
