package kr.ac.kpu.oosoosoo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_main.*
import kr.ac.kpu.oosoosoo.contents.ContentsActivity
import kr.ac.kpu.oosoosoo.home.HomeActivity
import kr.ac.kpu.oosoosoo.login.LoginActivity
import kr.ac.kpu.oosoosoo.netflix.NetflixAddWishActivity
import kr.ac.kpu.oosoosoo.netflix.NetflixWatchingActivity
import kr.ac.kpu.oosoosoo.netflix.NetflixWishesActivity
import kr.ac.kpu.oosoosoo.search.SearchActivity
import kr.ac.kpu.oosoosoo.watcha.WatchaAddWishActivity
import kr.ac.kpu.oosoosoo.watcha.WatchaWatchingActivity
import kr.ac.kpu.oosoosoo.watcha.WatchaWishesActivity
import kr.ac.kpu.oosoosoo.wavve.WavveWatchingActivity
import kr.ac.kpu.oosoosoo.wavve.WavveWishesActivity
import org.jetbrains.anko.startActivity
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AWS Auth Session 테스트 코드
        Amplify.Auth.fetchAuthSession(
            { Log.i("AWS AmplifyQuickstart", "Auth session = $it") },
            { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
        )

        btn_hash.setOnClickListener {
            var keyHash = Utility.getKeyHash(this)
            Log.d("Kakao", keyHash)
        }

        btn_main.setOnClickListener { view ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_contents -> startActivity(Intent(this, ContentsActivity::class.java))
                R.id.radio_netflix_wish -> startActivity(Intent(this, NetflixWishesActivity::class.java))
                R.id.radio_netflix_watching -> startActivity<NetflixWatchingActivity>()
                R.id.radio_netflix_add_wish -> startActivity<NetflixAddWishActivity>()
                R.id.radio_watcha_watching -> startActivity<WatchaWatchingActivity>()
                R.id.radio_watcha_wish -> startActivity<WatchaWishesActivity>()
                R.id.radio_watcha_add_wish -> startActivity<WatchaAddWishActivity>()
                R.id.radio_wavve_watching -> startActivity<WavveWatchingActivity>()
                R.id.radio_wavve_wish -> startActivity<WavveWishesActivity>()
            }
        }

        btn_to_login.setOnClickListener {
            startActivity<LoginActivity>()
        }

        btn_to_search.setOnClickListener {
            startActivity<SearchActivity>()
        }

        btn_to_home.setOnClickListener {
            startActivity<HomeActivity>()
        }

        //radio_netflix_add_wish.paintFlags = radio_wavve_watching.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}