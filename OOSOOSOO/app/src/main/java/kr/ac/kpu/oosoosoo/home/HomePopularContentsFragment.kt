package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.fragment_home_popular_contents.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardListAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.CardListData
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import kr.ac.kpu.oosoosoo.contents.RecommendContentInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomePopularContentsFragment : Fragment() {
    val contentsArrayList : MutableList<ContentInfo> = ArrayList() //모든 컨텐츠 리스트
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

        val call = RetrofitBuilder().callRecommendContents
        var listAdapter : ContentCardListAdapter

        home_popular_cardList_recyclerview.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        var input = HashMap<String, String>()
        input["email"] = Amplify.Auth.currentUser.username

        //인기 컨텐츠 출력
        call.getRecommendContents(input).enqueue(object : Callback<List<RecommendContentInfo>> {
            override fun onResponse(
                call: Call<List<RecommendContentInfo>>,
                response: Response<List<RecommendContentInfo>>
            ) {
                val recommenededContentsList = response.body()
                var genreList= ArrayList<String>()


                Log.d("home_recommend_contents", "통신 성공")

                if (recommenededContentsList != null) {
                    for (recommendedContent in recommenededContentsList) {
                        contentsArrayList.add(recommendedContent.recommend!!)   //Contents 리스트 셋팅
                        genreList = (genreList + (recommendedContent!!.recommend!!.genre?.substringBefore(" ")!!
                            .split(",")!!.distinct() as ArrayList<String>)).distinct() as ArrayList<String>
                        genreList.removeAll { it.trim()=="" }
                    }

                    for(genre in genreList){
                        val contentFilteredByGenre = ArrayList<ContentInfo>()
                        for(content in contentsArrayList) {
                            if(content.genre!!.contains(genre)) {
                                contentFilteredByGenre.add(content)
                            }
                        }
                        contentFilteredByGenre.shuffle()
                        contentCardRowList.add(
                            CardListData(
                                genre,
                                contentFilteredByGenre
                            )
                        )
                    }
                }


                listAdapter = ContentCardListAdapter(context!!, ArrayList(contentCardRowList.shuffled()), spanCount = 2)
                //부모 어댑터 지정(수직방향)
                home_popular_cardList_recyclerview.adapter = listAdapter
                home_popular_cardList_recyclerview.adapter!!.notifyDataSetChanged()


            }

            override fun onFailure(call: Call<List<RecommendContentInfo>>, t: Throwable) {
                Log.d("home_recommend_contents", t.message.toString())
            }
        })
    }

}