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
            holder.id.text = item[position].id.toString()
            holder.title.text = item[position].title.toString()
            holder.genre.text = item[position].genre.toString()
            holder.production_countries.text = item[position].production_countries.toString()
            holder.vote_count.text = "("+item[position].vote_count.toString()+")"
            holder.vote_average.text = item[position].vote_average.toString()
            holder.number_of_seasons.text = item[position].number_of_seasons.toString()
            holder.number_of_episodes.text = item[position].number_of_episodes.toString()
            holder.release_date.text = item[position].release_date.toString()
            holder.runtime.text = item[position].runtime.toString()+"분"
            holder.overview.text = item[position].overview.toString()
            holder.flatrate.text = item[position].flatrate.toString()
            holder.rent.text = item[position].rent.toString()
            holder.buy.text = item[position].buy.toString()

            if(item[position].number_of_seasons.toString() == "null") {
                holder.number_of_seasons.visibility = View.INVISIBLE
            }

            if(item[position].number_of_episodes.toString() == "null") {
                holder.number_of_episodes.visibility = View.INVISIBLE
            }
        }

        inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)) {
            //이너 클래스의 변수값 -> layout id 값과 연결
            val id = itemView.content_id
            val title = itemView.content_title
            val genre = itemView.content_genre
            val production_countries = itemView.content_production_countries
            val vote_count = itemView.content_vote_count
            val vote_average = itemView.content_vote_average
            val number_of_seasons = itemView.content_number_of_seasons
            val number_of_episodes = itemView.content_number_of_episodes
            val release_date = itemView.content_release_date
            val runtime = itemView.content_runtime
            val overview = itemView.content_overview
            val flatrate = itemView.content_flatrate
            val rent = itemView.content_rent
            val buy = itemView.content_buy
        }
    }
}