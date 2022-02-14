package kr.ac.kpu.oosoosoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val call = RetrofitBuilder().callSignUp  //Retrofit Call

        val nextintent = Intent(this, SelectIwActivity::class.java)

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
            input["age"] = edt_age.text.toString()
            input["job"] = edt_job.text.toString()              //nullable
            input["overview"] = edt_overview.text.toString()    //nullable

            if(input["email"] != "" && input["pwd"] != "" && input["name"] != "" &&
                input["phone_num"] != "" && input["nickname"] != "" && input["gender"] != "" && input["age"] != "" ){
                call.getSignUp(input).enqueue(object : Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Log.d("log_signup", t.message.toString())
                        signup_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                    }

                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        val body : Boolean? = response.body()
                        Log.d("log_signup", "통신 성공")
                        signup_tv.text = "통신 $body ..."

                        Log.d("log_signup", body.toString())
                        if (body != null) {
                            if(body == true){
                                signup_tv.text = "회원가입 $body !"
                                startActivity(nextintent)
                            } else {
                                signup_tv.text = "회원가입 $body !"
                            }
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