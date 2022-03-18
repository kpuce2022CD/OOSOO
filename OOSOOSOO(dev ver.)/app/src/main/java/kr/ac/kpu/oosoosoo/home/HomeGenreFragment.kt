package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_genre.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardListAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeGenreFragment : Fragment() {
    companion object {
        const val ROW_MAX_NUM = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_genre, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val call = RetrofitBuilder().callSearchTest
        val contentsArrayList : MutableList<ContentInfo> = ArrayList() //모든 컨텐츠 리스트
        val contentCardRowList : MutableList<CardListData> = ArrayList() //한 행의 컨텐츠 리스트

        home_genre_cardList_recyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        call.getSearchTest().enqueue(object : Callback<List<ContentInfo>> {

            override fun onResponse(
                call: Call<List<ContentInfo>>,
                response: Response<List<ContentInfo>>
            ) {
                val contents = response.body()
                var movieIndex = 1
                Log.d("home_genre_contents", "통신 성공")

                if (contents != null) {
                    for (content in contents) {
                        Log.d("home_genre_contents", content.toString())
                        contentsArrayList.add(content)   //Contents 리스트 셋팅
                    }
                }

                for(contentsRow in contentsArrayList.chunked(ROW_MAX_NUM)) {
                    contentCardRowList.add(
                        CardListData(
                            "Movie ${movieIndex++}",
                            ArrayList(contentsRow)
                        )
                    )
                }
                //부모 어댑터 지정(수직방향)
                home_genre_cardList_recyclerview.adapter =
                    ContentCardListAdapter(context!!, ArrayList(contentCardRowList))
                home_genre_cardList_recyclerview.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("home_genre_contents", t.message.toString())
            }
        })

    }

}