package kr.ac.kpu.oosoosoo.login


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_interworking.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
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

        //인텐트로 넘어온 값으로 화면 생성
        val platform_name = intent.getStringExtra("platform")
        val user_email = Amplify.Auth.currentUser.userId

        if (platform_name == "netflix") {
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
                input["u_email"] = user_email.toString()
                input["platform"] = platform_name.toString()
                input["id"] = i_id_edt.text.toString()
                input["passwd"] = i_pwd_edt.text.toString()
                input["profile_name"] = i_profileName_edt.text.toString()

                call.getInterworking(input).enqueue(object : Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        i_result_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                    }

                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        val body : Boolean? = response.body()
                        if (body.toString() == "null") {
                            i_result_tv.text = "서버 body = null"
                        } else {
                            if(body == true) {
                                val intent_result = Intent()
                                i_result_tv.text = "$platform_name 에 연동 로그인 성공!"
                                val intent_msg = platform_name
                                intent_result.putExtra("result", intent_msg)
                                setResult(Activity.RESULT_OK, intent_result)
                                finish()
                            } else {
                                i_result_tv.text = "*$platform_name 에 연동 로그인 실패*"
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