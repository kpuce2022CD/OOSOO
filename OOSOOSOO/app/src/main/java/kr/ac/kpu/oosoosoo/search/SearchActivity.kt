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

        val ottList = resources.getStringArray(R.array.ottList)
        val typeList = resources.getStringArray(R.array.typeList)
        val genreList = resources.getStringArray(R.array.genreList)

        var ottFilter : String = ""
        var typeFilter : String = ""
        var genreFilter : String = ""


        spinner_ott.adapter = ArrayAdapter(this, R.layout.spinner_item, ottList)
        spinner_type.adapter = ArrayAdapter(this, R.layout.spinner_item, typeList)
        spinner_genre.adapter = ArrayAdapter(this, R.layout.spinner_item, genreList)

        val contentsList = ArrayList<ContentInfo>()
        val originalList = ArrayList<ContentInfo>()

        val searchAdapter = SearchContentsAdapter(this, contentsList)
        recy_search.adapter = searchAdapter

        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        recy_search.layoutManager = gridLayoutManager

        val call = RetrofitBuilder().callSearchTitle

        btn_search.setOnClickListener {
            if (edt_search.length() != 0) {

                contentsList.clear()
                originalList.clear()

                var input = HashMap<String, String>()
                input["keyword"] = edt_search.text.toString()

                call.getSearchTitle(input).enqueue(object : Callback<List<ContentInfo>> {
                    override fun onResponse(call: Call<List<ContentInfo>>, response: Response<List<ContentInfo>>) {
                        val contents = response.body()

                        Log.d("search","통신 성공")

                        if (contents != null) {
                            for (content in contents) {
                                Log.d("search", content.toString())
                                contentsList.add(content)   //Contents 리스트 셋팅
                                originalList.add(content)

                                searchAdapter.notifyDataSetChanged()
                                recy_search.adapter = searchAdapter
                            }
                        }
                    }
                    override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                        Log.d("search", t.message.toString())
                    }
                })

            } else {
                toast("검색어를 입력하세요!")
            }
        }

        spinner_ott.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    ottFilter = ottList[position]
                }
                else {
                    ottFilter = ""
                }
                contentsList.clear()
                for (content in originalList) {
                    if (content._type != null && content.flatrate != null && content.genre != null) {
                        if (content.flatrate.contains(ottFilter)
                            && content._type.contains(typeFilter.lowercase())
                            && content.genre.contains(genreFilter)) {
                            contentsList.add(content)
                        }
                    }
                }
                searchAdapter.notifyDataSetChanged()
                recy_search.adapter = searchAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }
        }

        spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    typeFilter = typeList[position]
                }
                else {
                    typeFilter = ""
                }
                contentsList.clear()
                for (content in originalList) {
                    if (content._type != null && content.flatrate != null && content.genre != null) {
                        if (content._type.contains(typeFilter.lowercase())
                            && content.flatrate.contains(ottFilter)
                            && content.genre.contains(genreFilter)) {
                            contentsList.add(content)
                        }
                    }
                }
                searchAdapter.notifyDataSetChanged()
                recy_search.adapter = searchAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }
        }

        spinner_genre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    genreFilter = genreList[position]
                }
                else {
                    genreFilter = ""
                }
                contentsList.clear()
                for (content in originalList) {
                    if (content._type != null && content.flatrate != null && content.genre != null) {
                        if (content.genre.contains(genreFilter)
                            && content.flatrate.contains(ottFilter)
                            && content._type.contains(typeFilter.lowercase())) {
                            contentsList.add(content)
                        }
                    }
                }
                searchAdapter.notifyDataSetChanged()
                recy_search.adapter = searchAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do Nothing
            }
        }

    }
}
