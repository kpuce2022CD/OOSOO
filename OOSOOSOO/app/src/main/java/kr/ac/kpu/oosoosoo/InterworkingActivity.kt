package kr.ac.kpu.oosoosoo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_interworking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InterworkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interworking)

        val call = RetrofitBuilder().callInterworking  //Retrofit Call

        //돌아가기 버튼
        iw_back_btn.setOnClickListener {
            finish()
        }

        val platform_name = intent.getStringExtra("platform_name")
        val user_id = intent.getStringExtra("u_id")?.toInt()

        if (platform_name == "netfilx") {
            platform_img.setImageResource(R.drawable.netfilx_logo)
        } else if (platform_name == "watcha") {
            platform_img.setImageResource(R.drawable.watcha_logo)
        } else if (platform_name == "wavve") {
            platform_img.setImageResource(R.drawable.wavve_logo)
        } else if (platform_name == "tving") {
            platform_img.setImageResource(R.drawable.tving_logo)
        } else if (platform_name == "disney") {
            platform_img.setImageResource(R.drawable.disney_logo)
        } else {
            platform_img.visibility = View.INVISIBLE
        }
        i_platformName_tv.text = platform_name
        i_id_tv.text = platform_name + " 아이디"
        i_pwd_tv.text = platform_name + " 비밀번호"
        i_profileName_tv.text = platform_name + " 프로필 이름"

        //플랫폼 연동 시도
        i_login_btn.setOnClickListener {
            if (i_id_edt.length() != 0 && i_pwd_edt.length() != 0 && i_profileName_edt.length() != 0){
                var input = HashMap<String, String>()
                input["u_id"] = user_id.toString()
                input["platform"] = platform_name.toString()
                input["id"] = i_id_edt.text.toString()
                input["passwd"] = i_pwd_edt.text.toString()
                input["profile_name"] = i_profileName_edt.text.toString()

                call.getInterworking(input).enqueue(object : Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Log.d("log_interworking", t.message.toString())
                        i_result_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                    }

                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        val body : Boolean? = response.body()
                        Log.d("log_interworking", "통신 성공")
                        i_result_tv.text = "통신 실패"

                        Log.d("log_interworking", body.toString())
                        if (body != null) {
                            if(body == true){
                                val intent_result = Intent()
                                i_result_tv.text = "$platform_name 에 연동 로그인 성공!"
                                val intent_msg = platform_name + "_ok"
                                intent_result.putExtra("result", intent_msg)
                                Log.d("log_interworking", "$intent_msg")
                                setResult(Activity.RESULT_OK, intent_result)
                                finish()
                            }else{
                                i_result_tv.text = "*$platform_name 에 연동 로그인 실패*"
                                Log.d("log_interworking", "연동 로그인 실패")
                            }
                        }
                    }
                })
            } else {
                Toast.makeText(this, "에디트텍스트에 모든 정보 입력해주세요.", Toast.LENGTH_LONG).show()
            }
        }

    }
}