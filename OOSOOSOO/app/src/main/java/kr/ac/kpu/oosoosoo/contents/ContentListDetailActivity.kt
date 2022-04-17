package kr.ac.kpu.oosoosoo.contents

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_content_list_detail.*
import kotlinx.android.synthetic.main.activity_content_list_detail.view.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R

class ContentListDetailActivity : BaseActivity(TransitionMode.HORIZON) {
    lateinit var contentRowList : CardListData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list_detail)

        //contentRowList = intent.getParcelableExtra<CardListData>("cardListData")!!

        content_list_detail_title.text = contentRowList.cardListTitle

        content_list_detail_recyclerview.
        content_list_detail_back_btn.setOnClickListener{
            finish()
        }
    }
}