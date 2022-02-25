package kr.ac.kpu.oosoosoo.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_interworking.*
import kr.ac.kpu.oosoosoo.R


class SelectIwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_interworking)

        if (intent.hasExtra("email")){
            val user_email = intent.getStringExtra("email")
            u_email_tv.text = "유저 이메일: " + user_email.toString()

            netfilx_btn.setOnClickListener {
                val to_iw_intent = Intent(this, InterworkingActivity::class.java)
                to_iw_intent.putExtra("platform", "netflix")
                to_iw_intent.putExtra("u_email", user_email)
                startActivityForResult(to_iw_intent, 9898)
            }

            watcha_btn.setOnClickListener {
                val to_iw_intent = Intent(this, InterworkingActivity::class.java)
                to_iw_intent.putExtra("platform", "watcha")
                to_iw_intent.putExtra("u_email", user_email)
                startActivityForResult(to_iw_intent, 9898)
            }

            wavve_btn.setOnClickListener {
                val to_iw_intent = Intent(this, InterworkingActivity::class.java)
                to_iw_intent.putExtra("platform", "wavve")
                to_iw_intent.putExtra("u_email", user_email)
                startActivityForResult(to_iw_intent, 9898)
            }

            tving_btn.setOnClickListener {
                val to_iw_intent = Intent(this, InterworkingActivity::class.java)
                to_iw_intent.putExtra("platform", "tving")
                to_iw_intent.putExtra("u_email", user_email)
                startActivityForResult(to_iw_intent, 9898)
            }

            disney_btn.setOnClickListener {
                val to_iw_intent = Intent(this, InterworkingActivity::class.java)
                to_iw_intent.putExtra("platform", "disney")
                to_iw_intent.putExtra("u_email", user_email)
                startActivityForResult(to_iw_intent, 9898)
            }

            si_next_btn.setOnClickListener {
                //다음 화면 정해야함 어느 화면으로 넘기기
            }

        } else {
            Toast.makeText(this, "유저 아이디 전달 실패", Toast.LENGTH_LONG).show()
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
