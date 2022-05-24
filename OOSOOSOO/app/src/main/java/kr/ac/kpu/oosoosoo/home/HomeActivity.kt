package kr.ac.kpu.oosoosoo.home

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_home.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R

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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        nav_view.setupWithNavController(navController)

    }
}