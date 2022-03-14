package kr.ac.kpu.oosoosoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_intro_animation.*
import kr.ac.kpu.oosoosoo.home.HomeActivity
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
                startActivity<HomeActivity>()
                finish()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }
}