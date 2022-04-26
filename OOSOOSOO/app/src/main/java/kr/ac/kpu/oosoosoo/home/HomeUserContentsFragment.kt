package kr.ac.kpu.oosoosoo.home

import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_select_interworking.*
import kotlinx.android.synthetic.main.fragment_home_user_contents.*
import kotlinx.android.synthetic.main.recy_item_content_row.*
import kotlinx.android.synthetic.main.recy_item_content_row.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.ContentCardAdapter
import kr.ac.kpu.oosoosoo.adapters.ContentCardListAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.CardListData
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeUserContentsFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_home_user_contents, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var userEmail : String? = null
        var input = HashMap<String, String>()

        try {
            userEmail = Amplify.Auth.currentUser.username
        } finally { }

        input["email"] = userEmail!!

        val callInterworking = RetrofitBuilder().myInterworking
        val callWishList = RetrofitBuilder().callUserWishList
        val callWatchingLog = RetrofitBuilder().callUserWatchingLog
        val contentsArrayList : MutableList<ContentInfo> = ArrayList() //모든 컨텐츠 리스트
        val contentCardRowList : MutableList<CardListData> = ArrayList() //한 행의 컨텐츠 리스트

        home_usercontent_alert_imageView.setImageDrawable(resources.getDrawable(R.drawable.crying))
        callInterworking.callMyInterworking(input).enqueue(object: Callback<List<String>> {
            override fun onFailure(call: Call<List<String>>, t: Throwable){
                Log.d("interworking", t.message.toString())
            }
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>){
                val body: List<String>? = response.body()
                Log.d("interworking", "통신 성공")

                if(body!!.isEmpty()){
                    home_usercontent_alert_cardView.visibility = View.VISIBLE
                    home_usercontent_cardList_recyclerview.visibility = View.GONE
                } else {
                    home_usercontent_cardList_recyclerview.visibility = View.VISIBLE
                    home_usercontent_alert_cardView.visibility = View.GONE
                    home_usercontent_cardList_recyclerview.layoutManager =
                        LinearLayoutManager(activity, GridLayoutManager.VERTICAL, false)
                }
            }
        })


        //시청목록 출력 api
        callWatchingLog.getWatchingLog(input).enqueue(object : Callback<List<ContentInfo>> {

            override fun onResponse(
                call: Call<List<ContentInfo>>,
                response: Response<List<ContentInfo>>
            ) {
                val contents = response.body()
                Log.d("Load Watching Log", "$contents")

                contentsArrayList.clear() //초기화

                if (contents != null) {
                    for (content in contents) {
                        //Log.d("User Watching Log Contents", content.toString())
                        contentsArrayList.add(content)   //Contents 리스트 셋팅
                    }
                }
                //for(contentsRow in contentsArrayList.chunked(ROW_MAX_NUM)) {   : 정해진 갯수대로 나누기
                contentCardRowList.add(
                    CardListData(
                        "${userEmail}의 시청목록",
                        ArrayList(contentsArrayList)
                    )
                )

                //부모 어댑터 지정(수직방향)
                home_usercontent_cardList_recyclerview.adapter =
                    ContentCardListAdapter(
                        context!!,
                        ArrayList(contentCardRowList),
                        spanCount = 2
                    )
                home_usercontent_cardList_recyclerview.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("home_user_contents", t.message.toString())
            }
        })

        //찜목록 출력 api
        callWishList.getWishList(input).enqueue(object : Callback<List<ContentInfo>> {

            override fun onResponse(
                call: Call<List<ContentInfo>>,
                response: Response<List<ContentInfo>>
            ) {
                val contents = response.body()
                Log.d("Load WishList", "$contents")

                contentsArrayList.clear() //초기화

                if (contents != null) {
                    for (content in contents) {
                        //Log.d("User Wishlist Contents", content.toString())
                        contentsArrayList.add(content)   //Contents 리스트 셋팅
                    }
                }
                //for(contentsRow in contentsArrayList.chunked(ROW_MAX_NUM)) { : 정해진 갯수만큼 나누기
                contentCardRowList.add(
                    CardListData(
                        "${userEmail}의 찜목록",
                        ArrayList(contentsArrayList)
                    )
                )

                //부모 어댑터 지정(수직방향)
                home_usercontent_cardList_recyclerview.adapter =
                    ContentCardListAdapter(context!!, ArrayList(contentCardRowList))
                home_usercontent_cardList_recyclerview.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("home_user_contents", t.message.toString())
            }
        })



    }
}