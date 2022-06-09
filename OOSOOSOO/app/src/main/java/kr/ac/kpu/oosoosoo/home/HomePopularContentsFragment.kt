package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home_popular_contents.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardListAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.CardListData
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class HomePopularContentsFragment : Fragment() {
    val contentsArrayList : MutableList<ContentInfo> = ArrayList() //모든 컨텐츠 리스트
    val movieContentsList : MutableList<ContentInfo> = ArrayList() //영화 인기컨텐츠 리스트
    val tvContentsList : MutableList<ContentInfo> = ArrayList() //tv시리즈 인기컨텐츠 리스트
    val contentCardRowList : MutableList<CardListData> = ArrayList()//한 행의 컨텐츠 리스트

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
        val view = inflater.inflate(R.layout.fragment_home_popular_contents, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val call = RetrofitBuilder().callPopular
        var listAdapter : ContentCardListAdapter

        home_popular_cardList_recyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        //인기 컨텐츠 출력
        call.getPopularContents().enqueue(object : Callback<List<ContentInfo>> {
            override fun onResponse(
                call: Call<List<ContentInfo>>,
                response: Response<List<ContentInfo>>
            ) {
                val popularContentList = response.body()
                Log.d("home_popular_contents", "통신 성공")

                if (popularContentList != null) {
                    for (popularContent in popularContentList) {
                        contentsArrayList.add(popularContent)
                    }
                    var type : String?
                    for (popularContent in contentsArrayList) {
                        type = popularContent._type
                        if (type == "movie") {
                            movieContentsList.add(popularContent)
                        } else if (type == "tv") {
                            tvContentsList.add(popularContent)
                        }
                    }

                    contentCardRowList.add(
                        CardListData(
                            "인기 영화 컨텐츠",
                            movieContentsList as ArrayList<ContentInfo>
                        )
                    )
                    contentCardRowList.add(
                        CardListData(
                            "인기 TV 시리즈",
                            tvContentsList as ArrayList<ContentInfo>
                        )
                    )

                }

                listAdapter = ContentCardListAdapter(context!!, ArrayList(contentCardRowList), spanCount = 2)
                //부모 어댑터 지정(수직방향)
                home_popular_cardList_recyclerview.adapter = listAdapter
                home_popular_cardList_recyclerview.adapter!!.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("home_popular_contents", t.message.toString())
            }
        })
    }

}