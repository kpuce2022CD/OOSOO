package kr.ac.kpu.oosoosoo.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.SearchContentsAdapter
import kr.ac.kpu.oosoosoo.contents.ContentInfo

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var contentsList : ArrayList<ContentInfo>? = null

        val adapter = SearchContentsAdapter(this, contentsList)
        adapter.notifyDataSetChanged()
        recy_search.adapter = adapter

        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        recy_search.layoutManager = gridLayoutManager
    }
}
