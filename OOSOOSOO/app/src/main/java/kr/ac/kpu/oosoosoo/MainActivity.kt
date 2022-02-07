package kr.ac.kpu.oosoosoo

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main.setOnClickListener { view ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_contents -> startActivity(Intent(this, ContentsActivity::class.java))
                R.id.radio_netflix_wish -> startActivity(Intent(this, NetflixWishesActivity::class.java))

            }
        }

        btn_to_login.setOnClickListener {
            startActivity<LoginActivity>()
        }

        radio_jjim.paintFlags = radio_jjim.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        radio_review.paintFlags = radio_jjim.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        radio_users.paintFlags = radio_jjim.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        radio_watcha_watching.paintFlags = radio_jjim.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        radio_watcha_wish.paintFlags = radio_jjim.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


    }


}