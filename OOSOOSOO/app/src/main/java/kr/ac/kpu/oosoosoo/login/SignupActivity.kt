package kr.ac.kpu.oosoosoo.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_signup.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val call = RetrofitBuilder().callSignUp  //Retrofit Call

        var gender_checked = 0

        //인텐트로 넘어온 값들 미리 editText에 입력
        edt_name.setText(intent.getStringExtra("name"))
        edt_email.setText(intent.getStringExtra("email"))
        edt_pwd.setText(intent.getStringExtra("pwd"))
        edt_nickname.setText(intent.getStringExtra("nickname"))
        edt_phone.setText(intent.getStringExtra("mobile"))
        edt_birth.setText(intent.getStringExtra("birthday"))

        val platform_name = intent.getStringExtra("platform")
        val user_email = intent.getStringExtra("u_email")

        radio_gender.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId){
                R.id.radio_male -> gender_checked = 1

                R.id.radio_female -> gender_checked = 0
            }
        }

        //signup 파라미터 email, pwd, name, phone_num, nickname, gender, birthday, job, overview
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
                        signup_tv.text = "이메일 형식이 아닙니다."
                    } else if (input["pwd"]?.length!! < 8){
                        signup_tv.text = "비밀번호는 최소 8자리 이상이어야 합니다."
                    } else if (!Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", input["phone_num"])){
                        signup_tv.text = "핸드폰 번호 형식이 아닙니다."
                    } else {
                        call.getSignUp(input).enqueue(object : Callback<Boolean> {
                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                Log.d("log_signup", t.message.toString())
                                signup_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                            }
                            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                val body : Boolean? = response.body()
                                Log.d("log_signup", "통신 성공")
                                signup_tv.text = "통신 성공"

                                Log.d("log_signup", body.toString())
                                if (body == true) {
                                    signup_tv.text = "회원가입 성공 $body"
                                    val options = AuthSignUpOptions.builder()
                                        .userAttribute(AuthUserAttributeKey.email(), edt_email.text.toString())
                                        .build()
                                    Amplify.Auth.signUp(edt_email.text.toString(), edt_pwd.text.toString(), options,
                                        {
                                            Log.i("AWSAuthQuickStart", "Sign up succeeded: $it")
                                            startActivity<ConfirmSignUpActivity>(
                                                "email" to edt_email.text.toString()
                                            )
                                        },
                                        {
                                            Log.e("AWSAuthQuickStart", "Sign up failed", it)
                                        }
                                    )
                                } else {
                                    signup_tv.text = "회원가입 $body"
                                }
                            }
                        })
                    }
            }
            else{
                signup_tv.text = "필수 입력!!"
            }


        }
    }
}