package kr.ac.kpu.oosoosoo.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.SearchContentsAdapter
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.contents.ContentInfo
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        spinner_ott.adapter = ArrayAdapter.createFromResource(this, R.array.ottList, R.layout.spinner_item)
        spinner_type.adapter = ArrayAdapter.createFromResource(this, R.array.typeList, R.layout.spinner_item)
        spinner_genre.adapter = ArrayAdapter.createFromResource(this, R.array.genreList, R.layout.spinner_item)

        spinner_ott.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    //선택안함
                    0 -> {
                        toast(spinner_ott.selectedItem.toString())
                    }
                    //Netflix
                    1 -> {
                        toast(spinner_ott.selectedItem.toString())
                    }
                    //Watcha
                    2 -> {
                        toast(spinner_ott.selectedItem.toString())
                    }
                    //Wavve
                    3 -> {
                        toast(spinner_ott.selectedItem.toString())
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val contentsList : ArrayList<ContentInfo> = ArrayList()

        val searchAdapter = SearchContentsAdapter(this, contentsList)
        recy_search.adapter = searchAdapter

        val call = RetrofitBuilder().callSearchTest

        call.getSearchTest().enqueue(object : Callback<List<ContentInfo>> {
            override fun onResponse(call: Call<List<ContentInfo>>, response: Response<List<ContentInfo>>) {
                val contents = response.body()

                Log.d("search","통신 성공")

                if (contents != null) {
                    for (content in contents) {
                        Log.d("search", content.toString())
                        contentsList.add(content)   //Contents 리스트 셋팅
                    }
                }
            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("search", t.message.toString())
            }
        })

        btn_search.setOnClickListener {
            searchAdapter.notifyDataSetChanged()
            recy_search.adapter = searchAdapter
        }

        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        recy_search.layoutManager = gridLayoutManager
    }
}
