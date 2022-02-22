package kr.ac.kpu.oosoosoo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recy_item_content_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.home.ContentInfo

//자식 컨테이너 어댑터
class ContentCardAdapter(cardListData: List<ContentInfo>?): RecyclerView.Adapter<ContentCardAdapter.ViewHolder>() {

    private var contentList : List<ContentInfo>? = cardListData

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contentList!![adapterPosition].title!!)
            }
        }

        fun bind(result: ContentInfo) {
            itemView.card_item_title.text = result.title

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recy_item_content_card, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentList!![position])
    }

    override fun getItemCount(): Int = contentList!!.size

}
