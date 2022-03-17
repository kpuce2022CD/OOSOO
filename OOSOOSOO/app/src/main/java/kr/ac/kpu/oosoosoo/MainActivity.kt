package kr.ac.kpu.oosoosoo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        nav_view.setupWithNavController(navController)

        //임시 구조상 위치 -> 동기형 함수로 인한 오류
        //추후에 Home fragment 내부에서 해결하는 것으로 수정
        Amplify.Auth.fetchAuthSession(
            {
                if (it.isSignedIn) {
                    Log.d("AWS Auth E-Mail", Amplify.Auth.currentUser.username)
                    btn_home_login.setImageResource(R.drawable.ic_baseline_user)
                } else {
                    btn_home_login.setImageResource(R.drawable.ic_baseline_login_24)
                }
                invalidateOptionsMenu()
            },
            { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
        )
    }
}