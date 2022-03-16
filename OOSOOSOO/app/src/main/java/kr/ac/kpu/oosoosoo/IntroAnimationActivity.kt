package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_intro_animation.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kr.ac.kpu.oosoosoo.home.HomeActivity
import kr.ac.kpu.oosoosoo.login.LoginActivity
import org.jetbrains.anko.startActivity

//Splash : Main으로 시작하기 전 애니메이션을 통해 Intro를 담당
class IntroAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_animation)

        val titleAnim = AnimationUtils.loadAnimation(this, R.anim.anim_intro_title)
        val iconAnim = AnimationUtils.loadAnimation(this, R.anim.anim_intro_icon)

        intro_icon.startAnimation(iconAnim)
        intro_title.startAnimation(titleAnim)

        titleAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                Amplify.Auth.fetchAuthSession(
                    {
                        if (it.isSignedIn) {
                            startActivity<MainActivity>()
                        } else {
                            startActivity<LoginActivity>()
                        }
                    },
                    { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
                )
                finish()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }
}