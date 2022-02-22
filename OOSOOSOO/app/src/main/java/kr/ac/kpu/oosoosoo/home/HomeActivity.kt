package kr.ac.kpu.oosoosoo.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardListAdapter

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val testContentList : List<CardListData>? = ParentItemList()
        val contentCardListAdapter = ContentCardListAdapter(testContentList)

        cardList_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        cardList_recyclerview.adapter = contentCardListAdapter

    }

    private fun ParentItemList(): List<CardListData>? {
        val itemList: MutableList<CardListData> = ArrayList()
        val item = CardListData(
            "Title 1",
            ChildItemList()
        )
        itemList.add(item)
        val item1 = CardListData(
            "Title 2",
            ChildItemList()
        )
        itemList.add(item1)
        val item2 = CardListData(
            "Title 3",
            ChildItemList()
        )
        itemList.add(item2)
        val item3 = CardListData(
            "Title 4",
            ChildItemList()
        )
        itemList.add(item3)
        return itemList
    }

    private fun ChildItemList(): List<ContentInfo>? {
        val ChildItemList: MutableList<ContentInfo> = ArrayList()
        ChildItemList.add(ContentInfo(title="Card 1"))
        ChildItemList.add(ContentInfo(title="Card 2"))
        ChildItemList.add(ContentInfo(title="Card 3"))
        ChildItemList.add(ContentInfo(title="Card 4"))
        return ChildItemList
    }
}