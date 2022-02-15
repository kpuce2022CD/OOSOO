package kr.ac.kpu.oosoosoo

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AWS Auth Session 테스트 코드
        Amplify.Auth.fetchAuthSession(
            { Log.i("AWS AmplifyQuickstart", "Auth session = $it") },
            { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
        )

        btn_main.setOnClickListener { view ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_contents -> startActivity(Intent(this, ContentsActivity::class.java))
                R.id.radio_netflix_wish -> startActivity(Intent(this, NetflixWishesActivity::class.java))
                R.id.radio_netflix_watching -> startActivity<NetflixWatchingActivity>()
                R.id.radio_netflix_add_wish -> startActivity<NetflixAddWishActivity>()
                R.id.radio_watcha_watching -> startActivity<WatchaWatchingActivity>()
                R.id.radio_watcha_wish -> startActivity<WatchaWishesActivity>()
                R.id.radio_wavve_watching -> startActivity<WavveWatchingActivity>()
                R.id.radio_wavve_wish -> startActivity<WavveWishesActivity>()
            }
        }

        btn_to_login.setOnClickListener {
            startActivity<LoginActivity>()
        }

        //radio_netflix_add_wish.paintFlags = radio_wavve_watching.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}