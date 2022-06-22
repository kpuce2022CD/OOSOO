package kr.ac.kpu.oosoosoo.recommends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home_popular_contents.*
import kotlinx.android.synthetic.main.fragment_recommends.*
import kotlinx.android.synthetic.main.recy_item_recommend_card.*
import kotlinx.android.synthetic.main.recy_item_recommend_card.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.RecommendContentListAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.CardListData
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import kr.ac.kpu.oosoosoo.contents.RecommendContentInfo
import kr.ac.kpu.oosoosoo.contents.RecommendListData
import kr.ac.kpu.oosoosoo.search.SearchActivity
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.max

class TrendsFragment : Fragment() {
    val recommendContentsArrayList : ArrayList<RecommendContentInfo> = ArrayList() //모든 컨텐츠 리스트
    val recommendSetArrayList : MutableList<RecommendListData> = ArrayList()//한 행의 컨텐츠 리스트

    companion object {
        const val MAX_NUM = 30
        val recommendSetTitleList = listOf("\uD83E\uDD29 베스트 추천 20", "\uD83D\uDE06 나와 비슷한 유저가 좋아한 콘텐츠 20",
                "\uD83D\uDE0E 좋아할 수 있는 콘텐츠 20", "\uD83D\uDE00 볼만한 콘텐츠 20", "기타 추천 콘텐츠 1", "기타 추천 콘텐츠 2",
                "기타 추천 콘텐츠 3", "기타 추천 콘텐츠 4", "기타 추천 콘텐츠 5", "기타 추천 콘텐츠 6", "기타 추천 콘텐츠 7")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommends, container, false)

        //홈화면 검색 돋보기 버튼
        view.btn_home_search.setOnClickListener {
            requireContext().startActivity<SearchActivity>()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val call = RetrofitBuilder().callRecommendContents

        recommend_cardList_recyclerView.orientation =
            ViewPager2.ORIENTATION_VERTICAL

        var input = HashMap<String, String>()
        input["email"] = Amplify.Auth.currentUser.username

        //추천 컨텐츠 출력
        call.getRecommendContents(input).enqueue(object : Callback<List<RecommendContentInfo>> {
            override fun onResponse(
                call: Call<List<RecommendContentInfo>>,
                response: Response<List<RecommendContentInfo>>
            ) {
                val recommenededContentsList = response.body()

                Log.d("recommend_contents", "통신 성공")

                if (recommenededContentsList != null) {
                    for (recommendedContent in recommenededContentsList) {
                        recommendContentsArrayList.add(recommendedContent)
                    }
                }
                for ((i, testRecommendedContentsList) in recommendContentsArrayList.chunked(20).withIndex()) {
                    val recommendListSetData = RecommendListData(recommendSetTitleList[i%10], testRecommendedContentsList as ArrayList)
                    recommendSetArrayList.add(recommendListSetData)
                }

                /* 장르별로 구분하기
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
                 */

                val adapter = RecommendContentListAdapter(context!!, recommendSetArrayList as ArrayList<RecommendListData>)

                recommend_cardList_recyclerView.adapter = adapter
                recommend_cardList_recyclerView.adapter!!.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<List<RecommendContentInfo>>, t: Throwable) {
                Log.d("home_recommend_contents", t.message.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()




    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy", "꺼짐")
    }
}