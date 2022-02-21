package kr.ac.kpu.oosoosoo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val call = RetrofitBuilder().callSignUp  //Retrofit Call

        var gender_checked = 0

        radio_gender.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId){
                R.id.radio_male -> gender_checked = 1

                R.id.radio_female -> gender_checked = 0
            }
        }

        //signup 파라미터 email, pwd, name, phone_num, nickname, gender, age, job, overview
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
                input["phone_num"] != "" && input["nickname"] != "" && input["gender"] != "" && input["age"] != "" ){
                call.getSignUp(input).enqueue(object : Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Log.d("log_signup", t.message.toString())
                        signup_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        val body : Int? = response.body()
                        Log.d("log_signup", "통신 성공")
                        signup_tv.text = "통신 성공"

                        Log.d("log_signup", body.toString())
                        if (body != null) {
                            signup_tv.text = "회원가입 성공 u_id: $body"
                            val options = AuthSignUpOptions.builder()
                                .userAttribute(AuthUserAttributeKey.email(), edt_email.text.toString())
                                .build()
                            Amplify.Auth.signUp(edt_email.text.toString(), edt_pwd.text.toString(), options,
                                {
                                    Log.i("AWSAuthQuickStart", "Sign up succeeded: $it")
                                    startActivity<ConfirmSignUpActivity>(
                                        "u_id" to body,
                                        "email" to edt_email.text.toString()
                                    )
                                },
                                {
                                    Log.e("AWSAuthQuickStart", "Sign up failed", it)
                                }
                            )
                        } else {
                            signup_tv.text = "회원가입 실패 u_id: $body"
                        }
                    }
                })
            }
            else{
                signup_tv.text = "필수 입력!!"
            }


        }
    }
}