package kr.ac.kpu.oosoosoo.contents

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_content_list_detail.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardAdapter
import androidx.recyclerview.widget.RecyclerView

class ContentListDetailActivity : BaseActivity(TransitionMode.HORIZON) {
    lateinit var contentRowTitle : String
    lateinit var contentRowItemList : ArrayList<ContentInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list_detail)

        contentRowTitle = intent.getStringExtra("cardListTitle").toString()
        contentRowItemList = intent.getSerializableExtra("cardItemList") as ArrayList<ContentInfo>

        content_list_detail_title.text = contentRowTitle

        content_list_detail_back_btn.setOnClickListener{
            finish()
        }

        //자식 어댑터 지정(수평방향)
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL,false)
        content_list_detail_recyclerview.layoutManager = gridLayoutManager
        content_list_detail_recyclerview.setHasFixedSize(true)
        content_list_detail_recyclerview.adapter = ContentCardAdapter(this, contentRowItemList)
        //content_list_detail_recyclerview.addItemDecoration(GridSpacingItemDecoration(3,30))
    }
    inner class GridSpacingItemDecoration(spanCount: Int, spacing: Int) : RecyclerView.ItemDecoration(){
        private val spanCount = spanCount
        private val spacing = spacing

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val position: Int = parent.getChildAdapterPosition(view) // 아이템 array index
            val column = position % spanCount // 열개수

            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom

        }
    }
}