package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.amplifyframework.core.Amplify
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.adapters.PagerFragmentStateAdapter
import kr.ac.kpu.oosoosoo.login.LoginActivity
import kr.ac.kpu.oosoosoo.login.SelectIwActivity
import kr.ac.kpu.oosoosoo.search.SearchActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class HomeFragment : Fragment() {

    private val tabTitleArray = arrayOf(
        "사용자 추천 컨텐츠",
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

        //홈화면 로그인 버튼
        view.btn_home_login.setOnClickListener{
            Amplify.Auth.fetchAuthSession(
                {
                    if (it.isSignedIn) {
                        Log.d("AWS Auth E-Mail", Amplify.Auth.currentUser.username)
                        // 로그인 되어있을시 유저 정보 출력화면으로 이동하도록 변경 필요
                    } else {
                        requireContext().startActivity<LoginActivity>()
                    }
                },
                { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
            )
        }

        //홈화면 플랫폼 추가 버튼
        view.btn_home_add_platform.setOnClickListener{
            Amplify.Auth.fetchAuthSession(
                {
                    if (it.isSignedIn) {
                        Log.d("AWS Auth E-Mail", Amplify.Auth.currentUser.username)
                        requireContext().startActivity<SelectIwActivity>(
                            "email" to Amplify.Auth.currentUser.username
                        )
                    } else {
                        requireContext().toast("로그인 후 이용해주세요!")
                    }
                },
                { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
            )
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())

        // ***Fragment 추가***
        pagerAdapter.addFragment(HomeRecommendContentsFragment())
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