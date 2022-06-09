package kr.ac.kpu.oosoosoo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recommends.view.*
import kotlinx.android.synthetic.main.recy_item_recommend_set.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.contents.*
import kotlin.math.max


//자식 컨테이너 어댑터
class RecommendContentListAdapter(context: Context, recommendSetListData: ArrayList<RecommendListData>?): RecyclerView.Adapter<RecommendContentListAdapter.ViewHolder>() {
    //출력할 하나의 item List
    private var recommendSetList : ArrayList<RecommendListData> = recommendSetListData!!
    val context : Context = context


    var onItemClick: ((RecommendListData) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(recommendSetList[adapterPosition])
            }
        }

        //layout 파일에 값 출력
        fun bind(result: RecommendListData, adapter: RecommendContentAdapter) {
            itemView.item_recommend_set_title.text = result.recommendListTitle

            itemView.recommend_set_recyclerview.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            itemView.recommend_set_recyclerview.adapter = adapter

            itemView.recommend_set_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        var firstPos = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        var secondPos = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        var selectedPos = max(firstPos,secondPos)
                        if(selectedPos!=-1){
                            var viewItem = (recyclerView.layoutManager as LinearLayoutManager).findViewByPosition(selectedPos)
                            viewItem?.run{
                                var itemMargin = (recyclerView.measuredWidth-viewItem.measuredWidth)/2
                                recyclerView.smoothScrollBy( this.x.toInt()-itemMargin , 0)
                            }
                        }
                    }
                }
            })
        }
    }

    //출력할 xml파일 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.recy_item_recommend_set, parent,
            false
        )

    )

    //bind 과정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendSet = recommendSetList[position]
        val recommendContentListAdapter = RecommendContentAdapter(context, recommendSet.recommendItemList)
        holder.bind(recommendSet, recommendContentListAdapter)
    }

    override fun getItemCount(): Int {
        return recommendSetList.size
    }
}
