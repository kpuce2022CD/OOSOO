package kr.ac.kpu.oosoosoo

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kr.ac.kpu.oosoosoo.home.HomeActivity
import kr.ac.kpu.oosoosoo.login.LoginActivity
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    /* 역할 HomeActivity로 이양함

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        nav_view.setupWithNavController(navController)  */


        Amplify.Auth.fetchAuthSession(
            {
                if (it.isSignedIn) {
                    startActivity<HomeActivity>()
                    startActivity<IntroAnimationActivity>()
                    finish()
                } else {
                    startActivity<LoginActivity>()
                    startActivity<IntroAnimationActivity>()
                    finish()
                }
            },
            { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
        )


    }
}