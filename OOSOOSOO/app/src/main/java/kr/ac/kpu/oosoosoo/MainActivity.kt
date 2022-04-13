package kr.ac.kpu.oosoosoo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kr.ac.kpu.oosoosoo.home.HomeActivity
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
        startActivity<IntroAnimationActivity>()
        finish()
    }
}