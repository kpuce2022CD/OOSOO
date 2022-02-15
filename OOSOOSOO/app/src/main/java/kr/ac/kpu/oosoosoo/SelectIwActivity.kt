package kr.ac.kpu.oosoosoo

import android.app.Activity
import android.content.Intent
import org.jetbrains.anko.startActivityForResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select_interworking.*


class SelectIwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_interworking)

        var user_id = -1

        if (intent.hasExtra("u_id")){
            user_id = intent.getIntExtra("u_id", -1)
            u_id_tv.text = "유저 아이디: " + user_id.toString()
        } else {
            Toast.makeText(this, "유저 아이디 전달 실패", Toast.LENGTH_LONG).show()
        }



        netfilx_btn.setOnClickListener {
            startActivityForResult<InterworkingActivity>(
                9898,
                "platform" to "netfilx",
                "u_id" to user_id
            )
        }

        watcha_btn.setOnClickListener {
            startActivityForResult<InterworkingActivity>(
                9898,
                "platform" to "watcha",
                "u_id" to user_id
            )
        }

        wavve_btn.setOnClickListener {
            startActivityForResult<InterworkingActivity>(
                9898,
                "platform" to "wavve",
                "u_id" to user_id
            )
        }

        tving_btn.setOnClickListener {
            startActivityForResult<InterworkingActivity>(
                9898,
                "platform" to "tving",
                "u_id" to user_id
            )
        }

        disney_btn.setOnClickListener {
            startActivityForResult<InterworkingActivity>(
                9898,
                "platform" to "disney",
                "u_id" to user_id
            )
        }

        si_next_btn.setOnClickListener {
            //다음 화면 정해야함
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            9898 -> {
                if(resultCode == Activity.RESULT_OK){
                    var iw_check = data!!.getStringExtra("result")
                    Log.d("log_interworking_intent", "$iw_check")
                    if (iw_check == "netfilx_ok"){
                        iw_n_tv.text = "넷플 연동 완료"
                    } else if (iw_check == "watcha_ok"){
                        iw_w_tv.text = "왓챠 연동 완료"
                    } else if (iw_check == "wavve_ok"){
                        iw_wav_tv.text = "웨이브 연동 완료"
                    } else if (iw_check == "tving_ok"){
                        iw_t_tv.text = "티빙 연동 완료"
                    } else if (iw_check == "disney_ok"){
                        iw_d_tv.text = "디즈니플러스 연동 완료"
                    }
                } else{
                    Toast.makeText(this,"Intent받기 실패",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
