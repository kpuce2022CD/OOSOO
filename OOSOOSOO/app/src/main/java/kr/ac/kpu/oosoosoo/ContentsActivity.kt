package kr.ac.kpu.oosoosoo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_contents.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_item.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents)

        val contentsArrayList : ArrayList<ContentInfo> = ArrayList()  //Contents 리스트 변수

        val call = RetrofitBuilder().callContents  //Retrofit Call

        call.getContentInfo().enqueue(object : Callback<List<ContentInfo>> {

            override fun onResponse(call: Call<List<ContentInfo>>, response: Response<List<ContentInfo>>) {
                val contents = response.body()

                Log.d("movie","통신 성공")

                if (contents != null) {
                    for (content in contents) {
                        Log.d("movie", content.toString())
                        contentsArrayList.add(content)   //Contents 리스트 셋팅
                    }
                }

                viewPager_content.adapter = ContentsViewPagerAdapter(contentsArrayList) // 어댑터 생성
                viewPager_content.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("movie", t.message.toString())
            }
        })

        //돌아가기 버튼
        btn_contents_back.setOnClickListener{
            finish()
        }
    }
    inner class ContentsViewPagerAdapter(contentsList: ArrayList<ContentInfo>) : RecyclerView.Adapter<ContentsViewPagerAdapter.PagerViewHolder>() {
        var item = contentsList

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))


        override fun getItemCount(): Int = item.size

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            //각 item에 저장된 리스트 값 지정
            holder.id.text = item[position].c_id.toString()
            holder.genre.text = item[position].c_genre.toString()
            holder.name.text = item[position].c_name.toString()
            holder.release.text = item[position].c_release_date.toString()
            holder.cast.text = item[position].c_cast.toString()
            holder.ageLimit.text = item[position].c_age_limit.toString()
            holder.rating.text = item[position].c_rating.toString()
            holder.platform.text = item[position].c_platform.toString()
            holder.information.text = item[position].c_information.toString()
            holder.previewURL.text = item[position].c_preview_url.toString()
            holder.number.text = item[position].c_number.toString()
        }

        inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)) {
            //이너 클래스의 변수값 -> layout id 값과 연결
            val id = itemView.content_id
            val genre = itemView.content_genre
            val name = itemView.content_name
            val release = itemView.content_release
            val cast = itemView.content_cast
            val ageLimit = itemView.content_age_limit
            val rating = itemView.content_rating
            val platform = itemView.content_platform
            val information = itemView.content_information
            val previewURL = itemView.content_url
            val number = itemView.content_number
        }
    }
}