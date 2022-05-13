package kr.ac.kpu.oosoosoo.user

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.login.ConfirmSignUpActivity
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class UserUpdateActivity : BaseActivity(TransitionMode.NONE) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signup_call_btn.text = "회원 정보 수정"

        val call_info = RetrofitBuilder().callUserInfo
        val call_update = RetrofitBuilder().updateUser
        var input = HashMap<String, String>()

        input["email"] = Amplify.Auth.currentUser.username
        val userInfo : UserInfo //유저 정보
        var gender_checked = 0

        //유저 정보 불러오기 retrofit
        call_info.getUserInfo(input).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                Log.d("UserInfo", "통신 성공")

                if (userInfo != null) {
                    edt_email.setText(userInfo.email)
                    edt_pwd.setText(userInfo.passwd)
                    edt_name.setText(userInfo.name)
                    edt_phone.setText(userInfo.phone_number)
                    edt_nickname.setText(userInfo.nickname)
                    edt_birth.setText(userInfo.birthday)
                    edt_job.setText(userInfo.job)
                    edt_overview.setText(userInfo.overview)
                    edt_email.isClickable = false
                    edt_email.isFocusable = false
                    edt_email.setTextColor(Color.GRAY)
                    if(userInfo.gender == 1) {
                        radio_male.isChecked = true
                    } else {
                        radio_female.isChecked = true
                    }
                }

            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("User Info Fail", t.message.toString())
            }
        })

        radio_gender.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId){
                R.id.radio_male -> gender_checked = 1

                R.id.radio_female -> gender_checked = 0
            }
        }

        // 회원정보수정 버튼 눌렀을 때
        // 회원정보수정 retrofit
        signup_call_btn.setOnClickListener {
            var input = HashMap<String, String>()
            input["email"] = edt_email.text.toString()
            input["pwd"] = edt_pwd.text.toString()
            input["name"] = edt_name.text.toString()
            input["phone_num"] = edt_phone.text.toString()
            input["nickname"] = edt_nickname.text.toString()
            input["gender"] = gender_checked.toString()
            input["birthday"] = edt_birth.text.toString()
            input["job"] = edt_job.text.toString()              //nullable
            input["overview"] = edt_overview.text.toString()    //nullable

            if(input["email"] != "" && input["pwd"] != "" && input["name"] != "" &&
                input["phone_num"] != "" && input["nickname"] != "" && input["gender"] != "" && input["birthday"] != "" ){
                //정규식 예외처리
                if(!Patterns.EMAIL_ADDRESS.matcher(input["email"]).matches()){
                    Toast.makeText(this, "이메일 형식이 아닙니다.", Toast.LENGTH_LONG).show()
                } else if (input["pwd"]?.length!! < 8){
                    Toast.makeText(this, "비밀번호는 최소 8자리 이상이어야 합니다.", Toast.LENGTH_LONG).show()
                } else if (!Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", input["phone_num"])){
                    Toast.makeText(this, "핸드폰 번호 형식이 아닙니다.", Toast.LENGTH_LONG).show()
                } else {
                    call_update.updateUser(input).enqueue(object : Callback<Boolean> {
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Log.d("log_update_user", t.message.toString())
                            Toast.makeText(this@UserUpdateActivity, "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요.", Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            val body : Boolean? = response.body()
                            Log.d("log_update_user", "통신 성공")

                            Log.d("log_update_user", body.toString())
                            if (body == true) {
                                Toast.makeText(this@UserUpdateActivity, "회원정보 수정 성공", Toast.LENGTH_LONG).show()
                                finish()
                            } else {
                                Toast.makeText(this@UserUpdateActivity, "회원정보 수정 실패", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }
            else{
                Toast.makeText(this@UserUpdateActivity, "필수 입력 칸을 다 채워야합니다.", Toast.LENGTH_LONG).show()
            }
        }
    }
}