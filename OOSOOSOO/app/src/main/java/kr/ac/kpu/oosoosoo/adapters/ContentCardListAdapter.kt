package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_content_list_detail.view.*
import kotlinx.android.synthetic.main.recy_item_content_row.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.CardListData
import kr.ac.kpu.oosoosoo.contents.ContentListDetailActivity
import org.jetbrains.anko.startActivity

//부모 컨테이너 어댑터
class ContentCardListAdapter(context: Context, cardRowData: ArrayList<CardListData>?, transitionMode: Int = TransitionMode.HORIZONTAL,
    spanCount: Int = 1): RecyclerView.Adapter<ContentCardListAdapter.ViewHolder>() {

    //출력할 하나의 item List
    private var contentRowList : ArrayList<CardListData> = cardRowData!!
    val context : Context = context
    val transitionMode = transitionMode
    val spanCount = spanCount

    var onItemClick: ((CardListData) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(contentRowList[adapterPosition])
            }
        }

        //layout 파일에 값 출력
        fun bind(result: CardListData, adapter: ContentCardAdapter, transitionMode: Int, spanCount: Int) {
            //수평 모드일 경우(HomeFragment 자식 Fragment 둘이 사용)
            if(transitionMode == TransitionMode.HORIZONTAL) {
                itemView.item_row_title.text = result.cardListTitle

                //자식 어댑터 지정(수평방향)
                val gridLayoutManager = GridLayoutManager(itemView.context, spanCount,GridLayoutManager.HORIZONTAL,false)
                itemView.card_recyclerview.layoutManager = gridLayoutManager
                itemView.card_recyclerview.setHasFixedSize(true)
                itemView.card_recyclerview.adapter = adapter
            } else if(transitionMode == TransitionMode.VERTICAL) {
                itemView.content_list_detail_title.text = result.cardListTitle

                //자식 어댑터 지정(수직방향)
                val gridLayoutManager = GridLayoutManager(itemView.context, spanCount,GridLayoutManager.VERTICAL,false)
                itemView.content_list_detail_recyclerview.layoutManager = gridLayoutManager
                itemView.content_list_detail_recyclerview.setHasFixedSize(true)
                itemView.content_list_detail_recyclerview.adapter = adapter
            }

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
        holder.bind(contentRowList[position], contentCardAdapter, transitionMode, spanCount)
        holder.itemView.item_row_title.setOnClickListener(ViewRowDetailListener(contentRowList[position]))
    }

    override fun getItemCount(): Int = contentRowList.size

    //Content Row의 제목이나 "전체보기" 버튼 클릭 리스너
    inner class ViewRowDetailListener(contentCardRowList: CardListData) : View.OnClickListener{
        private val contentCardRowList = contentCardRowList

        override fun onClick(v: View?) {
            context!!.startActivity<ContentListDetailActivity>(
                "cardListData" to contentCardRowList
            )
        }
    }
}
