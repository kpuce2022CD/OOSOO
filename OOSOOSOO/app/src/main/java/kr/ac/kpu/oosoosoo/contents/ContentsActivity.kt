package kr.ac.kpu.oosoosoo.contents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_contents.*
import kotlinx.android.synthetic.main.content_item.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
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
                val filteredContent : ContentInfo?

                Log.d("movie","통신 성공")

                if (contents != null) {
                    for (content in contents) {
                        Log.d("movie", content.toString())
                        contentsArrayList.add(content)   //Contents 리스트 셋팅
                    }
                }

                Log.d("test extra", intent.getStringExtra("title").toString())
                if(intent.hasExtra("title")) {
                    try {
                        filteredContent = contentsArrayList.find{ it.title == intent.getStringExtra("title").toString() }
                        contentsArrayList.clear()
                        contentsArrayList.add(filteredContent!!)
                    } finally {   }
                }

                viewPager_content.adapter = ContentsViewPagerAdapter(contentsArrayList) // 어댑터 생성
                viewPager_content.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로

                Thread.sleep(1000)


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
    inner class ContentsViewPagerAdapter(contentsList: ArrayList<ContentInfo>) : RecyclerView.Adapter<ContentsViewPagerAdapter.PagerViewHolder>(), Filterable{

        private var originalContentsArrayList = contentsList
        //필터링을 위해 필요한 변수
        private var filterContentsArrayList: ArrayList<ContentInfo>? = contentsList

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

        override fun getItemCount(): Int = originalContentsArrayList.size

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            //각 item에 저장된 리스트 값 지정
            holder.id.text = originalContentsArrayList[position].id.toString()
            holder.title.text = originalContentsArrayList[position].title.toString()
            holder.genre.text = originalContentsArrayList[position].production_countries.toString()
            holder.vote_count.text = "("+originalContentsArrayList[position].vote_count.toString()+")"
            holder.vote_average.text = originalContentsArrayList[position].vote_average.toString()
            holder.number_of_seasons.text = originalContentsArrayList[position].number_of_seasons.toString()
            holder.number_of_episodes.text = originalContentsArrayList[position].number_of_episodes.toString()
            holder.release_date.text = originalContentsArrayList[position].release_date.toString()
            holder.runtime.text = originalContentsArrayList[position].runtime.toString()+"분"
            holder.overview.text = originalContentsArrayList[position].overview.toString()
            holder.flatrate.text = originalContentsArrayList[position].flatrate.toString()
            holder.rent.text = originalContentsArrayList[position].rent.toString()
            holder.buy.text = originalContentsArrayList[position].buy.toString()

            if(originalContentsArrayList[position].number_of_seasons.toString() == "null") {
                holder.number_of_seasons.visibility = View.INVISIBLE
            }

            if(originalContentsArrayList[position].number_of_episodes.toString() == "null") {
                holder.number_of_episodes.visibility = View.INVISIBLE
            }
        }

        inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.content_item, parent, false)) {
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

        //ViewPager2에서는 아직 지원하지 않음 (이후 RecyclerView 나 동적 어댑터에서 적용될 예정)
        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    Log.d("test Filter", "Doing Filtering")
                    val charString = constraint.toString()
                    filterContentsArrayList = if (charString.isEmpty()) {
                        originalContentsArrayList
                    } else {
                        val filteringList = ArrayList<ContentInfo>()
                        for (item in originalContentsArrayList!!) {
                            if (item!!.title!!.replace(" ","") == charString.replace(" ", "")) filteringList.add(item)
                        }
                        filteringList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filterContentsArrayList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    filterContentsArrayList = results?.values as ArrayList<ContentInfo>?
                    notifyDataSetChanged()
                }
            }

        }
    }
}