package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_home.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.recommends.RecommendsFragment
import kr.ac.kpu.oosoosoo.setting.SettingFragment

private const val TAG_HOME_FRAGMENT = "home_fragment"
private const val TAG_RECOMMEND_FRAGMENT = "recommend_fragment"
private const val TAG_SETTING_FRAGMENT = "setting_fragment"

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Amplify.Auth.fetchAuthSession(
            {
                if (it.isSignedIn) {
                } else {
                    finish()
                }
            },
            { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
        )

        setFragment(TAG_HOME_FRAGMENT, HomeFragment())

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> setFragment(TAG_HOME_FRAGMENT, HomeFragment())
                R.id.navigation_trends -> setFragment(TAG_RECOMMEND_FRAGMENT, RecommendsFragment())
                R.id.navigation_setting -> setFragment(TAG_SETTING_FRAGMENT, SettingFragment())
            }

            true
        }

        /*val navController = findNavController(R.id.nav_host_fragment)

        //프래그먼트 가져오기
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        //별도의 navigator 생성(custom) 및 셋업
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator

        //navigation graph도 설정
        navController.setGraph(R.navigation.mobile_navigation)

        //navigation 컨트롤러와 뷰를 연결
        nav_view.setupWithNavController(navController)*/

    }

    /* Fragment State 유지 함수 */
    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.nav_host_fragment, fragment, tag)
        }

        val home = manager.findFragmentByTag(TAG_HOME_FRAGMENT)
        val records = manager.findFragmentByTag(TAG_RECOMMEND_FRAGMENT)
        val campaign = manager.findFragmentByTag(TAG_SETTING_FRAGMENT)

        // Hide all Fragment
        if (home != null) {
            ft.hide(home)
        }
        if (records != null) {
            ft.hide(records)
        }
        if (campaign != null) {
            ft.hide(campaign)
        }

        if (tag == TAG_HOME_FRAGMENT) {
            if (home != null) {
                ft.show(home)
            }
        }
        if (tag == TAG_RECOMMEND_FRAGMENT) {
            if (records != null) {
                ft.show(records)
            }
        }
        if (tag == TAG_SETTING_FRAGMENT) {
            if (campaign != null) {
                ft.show(campaign)
            }
        }

        ft.commitAllowingStateLoss()
    }
}