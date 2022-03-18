package kr.ac.kpu.oosoosoo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(
    private val transitionMode: TransitionMode = TransitionMode.NONE
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.anim_frame_horizon_enter, R.anim.anim_frame_none)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.anim_frame_vertical_enter, R.anim.anim_frame_none)
            else -> Unit
        }
    }

    override fun finish() {
        super.finish()

        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.anim_frame_none, R.anim.anim_frame_horizon_exit)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.anim_frame_none, R.anim.anim_frame_vertical_exit)
            else -> Unit
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing) {
            when (transitionMode) {
                TransitionMode.HORIZON -> overridePendingTransition(R.anim.anim_frame_none, R.anim.anim_frame_horizon_exit)
                TransitionMode.VERTICAL -> overridePendingTransition(R.anim.anim_frame_none, R.anim.anim_frame_vertical_exit)
                else -> Unit
            }
        }
    }

    enum class TransitionMode {
        NONE,
        HORIZON,
        VERTICAL
    }
}