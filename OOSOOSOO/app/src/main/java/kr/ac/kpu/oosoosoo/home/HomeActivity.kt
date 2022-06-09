package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_home.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.navigation.KeepStateNavigator

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

        val navController = findNavController(R.id.nav_host_fragment)

        //프래그먼트 가져오기
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        //별도의 navigator 생성(custom) 및 셋업
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator

        //navigation graph도 설정
        navController.setGraph(R.navigation.mobile_navigation)

        //navigation 컨트롤러와 뷰를 연결
        nav_view.setupWithNavController(navController)

    }
}