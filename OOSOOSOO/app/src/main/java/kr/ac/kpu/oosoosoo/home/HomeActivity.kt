package kr.ac.kpu.oosoosoo.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardListAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    companion object{
        //한 행의 최대 컨텐츠 갯수
        const val ROW_MAX_NUM = 20
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val call = RetrofitBuilder().callSearchTest
        val contentsArrayList : MutableList<ContentInfo> = ArrayList() //모든 컨텐츠 리스트
        val contentCardRowList : MutableList<CardListData> = ArrayList() //한 행의 컨텐츠 리스트

        cardList_recyclerview.layoutManager =
            LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)

        /* [로직 요약]
         1. contentsArrayList에 모든 컨텐츠 저장
         2. 1줄에 20개씩 분할
         3. for문을 통해 CardListData 생성 후 RowList에 추가
         4. 어댑터 지정
         */
        call.getSearchTest().enqueue(object : Callback<List<ContentInfo>> {

            override fun onResponse(
                call: Call<List<ContentInfo>>,
                response: Response<List<ContentInfo>>
            ) {
                val contents = response.body()
                var movieIndex = 1
                Log.d("home_contents", "통신 성공")

                if (contents != null) {
                    for (content in contents) {
                        Log.d("home_contents", content.toString())
                        contentsArrayList.add(content)   //Contents 리스트 셋팅

                    }
                }


                for(contentsRow in contentsArrayList.chunked(20)) {
                    contentCardRowList.add(
                        CardListData(
                            "Movie ${movieIndex++}",
                            contentsRow
                        )
                    )
                    Log.d("test",contentsRow.toString())
                }
                //부모 어댑터 지정(수직방향)
                cardList_recyclerview.adapter =
                    ContentCardListAdapter(this@HomeActivity, contentCardRowList)
                cardList_recyclerview.adapter!!.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("movie", t.message.toString())
            }
        })




    }

    //아래부터는 테스트 코드 (For Front)
    /*private fun ParentItemList(): List<CardListData>? {
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
    }*/
}