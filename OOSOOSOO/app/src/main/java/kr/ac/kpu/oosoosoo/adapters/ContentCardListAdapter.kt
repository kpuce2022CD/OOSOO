package kr.ac.kpu.oosoosoo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recy_item_content_row.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.home.CardListData

//부모 컨테이너 어댑터
class ContentCardListAdapter(cardRowData: List<CardListData>?): RecyclerView.Adapter<ContentCardListAdapter.ViewHolder>() {

    private var contentRowList : List<CardListData> = cardRowData!!

    var onItemClick: ((CardListData) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contentRowList[adapterPosition])
            }
        }

        fun bind(result: CardListData, adapter: ContentCardAdapter) {
            itemView.item_row_title.text = result.cardListTitle
            itemView.card_recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            itemView.card_recyclerview.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recy_item_content_row, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contentCardAdapter = ContentCardAdapter(contentRowList[position].cardItemList)
        holder.bind(contentRowList[position], contentCardAdapter)
    }

    override fun getItemCount(): Int = contentRowList.size

}
