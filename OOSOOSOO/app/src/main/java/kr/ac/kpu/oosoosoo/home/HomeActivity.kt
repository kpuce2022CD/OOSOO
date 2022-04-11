package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.IntroAnimationActivity
import kr.ac.kpu.oosoosoo.R
import org.jetbrains.anko.startActivity

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //시작 애니메이션
        startActivity<IntroAnimationActivity>()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        nav_view.setupWithNavController(navController)

        Amplify.Auth.fetchAuthSession(
            {
                if (it.isSignedIn) {
                    Log.d("AWS Auth E-Mail", Amplify.Auth.currentUser.username)
                    btn_home_login.setImageResource(R.drawable.ic_baseline_user)
                } else {
                    btn_home_login.setImageResource(R.drawable.ic_baseline_login_24)
                }
            },
            { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
        )
    }

    public fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }

    public fun removeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.remove(fragment).commit()
    }
}