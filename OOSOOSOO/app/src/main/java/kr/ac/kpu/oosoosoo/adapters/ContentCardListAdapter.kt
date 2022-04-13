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

    //로딩시 버퍼 item List
    private var contentBufferList: ArrayList<ContentInfo?> = ArrayList()
    var isLoading = false

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
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        VIEW_TYPE_ITEM -> 1
                        VIEW_TYPE_LOADING -> 2 //number of columns of the grid
                        else -> -1
                    }
                }
            }


            /*itemView.card_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager?

                    if (!isLoading) {
                        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
                        if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == contentBufferList.size - 1) {
                            //추가 후 로딩
                            loadMore(adapter.contentList!!, adapter)
                            Log.d("testest", "test")
                            isLoading = true
                        }
                    }
                }
            })*/
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

    private fun loadMore(contentItemList: ArrayList<ContentInfo?>, adapter: ContentCardAdapter) {
        contentBufferList.add(contentItemList[0])
        adapter.notifyItemInserted(contentBufferList.size - 1)
        val handler = Handler()
        handler.postDelayed({
            contentBufferList.removeAt(contentBufferList.size - 1)
            val scrollPosition: Int = contentBufferList.size
            adapter.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            val nextLimit = currentSize + 10
            while (currentSize < nextLimit + 1) {
                contentBufferList.add(contentItemList[currentSize])
                currentSize++
            }
            adapter.notifyDataSetChanged()
            isLoading = false
        }, 20)
    }
}
