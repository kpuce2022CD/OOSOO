package kr.ac.kpu.oosoosoo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_select_interworking.*


class SelectIwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_interworking)

        val intent_iw = Intent(this, InterworkingActivity::class.java)

        netfilx_btn.setOnClickListener {
            intent_iw.putExtra("platform", "netfilx")
            startActivityForResult(intent_iw, 9898)
        }

        watcha_btn.setOnClickListener {
            intent_iw.putExtra("platform", "watcha")
            startActivity(intent_iw)
        }

        wavve_btn.setOnClickListener {
            intent_iw.putExtra("platform", "wavve")
            startActivity(intent_iw)
        }

        tving_btn.setOnClickListener {
            intent_iw.putExtra("platform", "tving")
            startActivity(intent_iw)
        }

        disney_btn.setOnClickListener {
            intent_iw.putExtra("platform", "disney")
            startActivity(intent_iw)
        }

        si_next_btn.setOnClickListener {
            //다음 화면 정해야함
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 9898){
            var iw_check = data!!.getStringExtra("iw_status")
            if (iw_check == "netfilx_ok"){
                iw_n_tv.text = "넷플 연동 완료"
            } else if (iw_check == "watcha_ok"){
                iw_w_tv.text = "왓챠 연동 완료"
            } else if (iw_check == "wavve_ok"){
                iw_w_tv.text = "웨이브 연동 완료"
            } else if (iw_check == "tving_ok"){
                iw_w_tv.text = "티빙 연동 완료"
            } else if (iw_check == "disney_ok"){
                iw_w_tv.text = "디즈니플러스 연동 완료"
            }
        }
    }
}
