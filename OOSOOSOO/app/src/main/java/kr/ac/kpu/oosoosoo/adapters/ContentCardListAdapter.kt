package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recy_item_content_row.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.Constant.VIEW_TYPE_ITEM
import kr.ac.kpu.oosoosoo.adapters.Constant.VIEW_TYPE_LOADING
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import kr.ac.kpu.oosoosoo.home.CardListData

//부모 컨테이너 어댑터
class ContentCardListAdapter(context: Context, cardRowData: ArrayList<CardListData>?): RecyclerView.Adapter<ContentCardListAdapter.ViewHolder>() {

    //출력할 하나의 item List
    private var contentRowList : ArrayList<CardListData> = cardRowData!!
    val context : Context = context

    var onItemClick: ((CardListData) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contentRowList[adapterPosition])
            }
        }

        //layout 파일에 값 출력
        fun bind(result: CardListData, adapter: ContentCardAdapter) {
            itemView.item_row_title.text = result.cardListTitle

            //자식 어댑터 지정(수평방향)
            val gridLayoutManager = GridLayoutManager(itemView.context, 2,GridLayoutManager.HORIZONTAL,false)
            itemView.card_recyclerview.layoutManager = gridLayoutManager
            itemView.card_recyclerview.setHasFixedSize(true)
            itemView.card_recyclerview.adapter = adapter
        }
    }

    //출력할 xml파일 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recy_item_content_row, parent,
            false
        )

    )

    //bind 과정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contentCardAdapter = ContentCardAdapter(context, contentRowList!![position].cardItemList)
        holder.bind(contentRowList[position], contentCardAdapter)
    }

    override fun getItemCount(): Int = contentRowList.size
}
