package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.PagerFragmentStateAdapter
import kr.ac.kpu.oosoosoo.search.SearchActivity
import org.jetbrains.anko.startActivity

class HomeFragment : Fragment() {

    private val tabTitleArray = arrayOf(
        "인기 컨텐츠",
        "찜 & 보고있는 컨텐츠"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)

        //홈화면 검색 돋보기 버튼
        view.btn_home_search.setOnClickListener {
            requireContext().startActivity<SearchActivity>()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())

        // ***Fragment 추가***
        pagerAdapter.addFragment(HomePopularContentsFragment())
        pagerAdapter.addFragment(HomeUserContentsFragment())

        // 어댑터 연결
        viewPager_home.adapter = pagerAdapter

        viewPager_home.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("ViewPagerFragment", "Page ${position+1}")
            }
        })

        viewPager_home.isUserInputEnabled = false

        // TabLayout attach
        TabLayoutMediator(tabLayout_home, viewPager_home) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }


}