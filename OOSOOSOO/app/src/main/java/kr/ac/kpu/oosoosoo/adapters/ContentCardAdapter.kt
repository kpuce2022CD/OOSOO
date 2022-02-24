package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recy_item_content_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.ContentInfo

//자식 컨테이너 어댑터
class ContentCardAdapter(context: Context, cardListData: List<ContentInfo>?): RecyclerView.Adapter<ContentCardAdapter.ViewHolder>() {

    //출력할 하나의 item List
    private var contentList : List<ContentInfo>? = cardListData

    val context : Context = context

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contentList!![adapterPosition].title!!)
            }
        }

        //layout 파일에 값 출력
        fun bind(result: ContentInfo, context: Context) {
            itemView.card_item_title.text = result.title

            val url = "https://image.tmdb.org/t/p/original" + contentList!![position].poster_path

            Glide.with(context)
                .load(url)
                .into(itemView.img_card_item)
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
        holder.bind(contentList!![position], context)
    }

    override fun getItemCount(): Int = contentList!!.size

}
