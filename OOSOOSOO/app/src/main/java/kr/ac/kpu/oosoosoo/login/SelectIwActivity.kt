package kr.ac.kpu.oosoosoo.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_select_interworking.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.MainActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.home.HomeActivity
import org.jetbrains.anko.startActivity


class SelectIwActivity : BaseActivity(TransitionMode.HORIZON) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_interworking)


        netfilx_btn.setOnClickListener {
            val to_iw_intent = Intent(this, InterworkingActivity::class.java)
            to_iw_intent.putExtra("platform", "netflix")
            startActivityForResult(to_iw_intent, 9898)
        }

        watcha_btn.setOnClickListener {
            val to_iw_intent = Intent(this, InterworkingActivity::class.java)
            to_iw_intent.putExtra("platform", "watcha")
            startActivityForResult(to_iw_intent, 9898)
        }

        wavve_btn.setOnClickListener {
            val to_iw_intent = Intent(this, InterworkingActivity::class.java)
            to_iw_intent.putExtra("platform", "wavve")
            startActivityForResult(to_iw_intent, 9898)
        }

        tving_btn.setOnClickListener {
            val to_iw_intent = Intent(this, InterworkingActivity::class.java)
            to_iw_intent.putExtra("platform", "tving")
            startActivityForResult(to_iw_intent, 9898)
        }

        disney_btn.setOnClickListener {
            val to_iw_intent = Intent(this, InterworkingActivity::class.java)
            to_iw_intent.putExtra("platform", "disney")
            startActivityForResult(to_iw_intent, 9898)
        }

        iw_next_btn.setOnClickListener {
            startActivity<HomeActivity>()
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                9898 -> {
                    var iw_check = data!!.getStringExtra("result")
                    if (iw_check == "netfilx") {
                        iw_n_tv.text = "넷플 연동 완료"
                    } else if (iw_check == "watcha") {
                        iw_w_tv.text = "왓챠 연동 완료"
                    } else if (iw_check == "wavve") {
                        iw_wav_tv.text = "웨이브 연동 완료"
                    } else if (iw_check == "tving") {
                        iw_t_tv.text = "티빙 연동 완료"
                    } else if (iw_check == "disney") {
                        iw_d_tv.text = "디즈니플러스 연동 완료"
                    } else {
                        Toast.makeText(this, "$iw_check", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
