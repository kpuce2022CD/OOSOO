package kr.ac.kpu.oosoosoo.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup_btn.setOnClickListener {
            startActivity<SignupActivity>()
        }

        val call = RetrofitBuilder().callLogin  //Retrofit Call

        //파라미터 email, pwd
        login_btn.setOnClickListener {
            var input = HashMap<String, String>()
            input["email"] = login_email.text.toString()
            input["pwd"] = login_pwd.text.toString()

            call.getSignIn(input).enqueue(object : Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("log_login", t.message.toString())
                    signin_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean> ) {
                    val body : Boolean? = response.body()
                    Log.d("log_login", "통신 성공")
                    signin_tv.text = ""

                    Log.d("log_login", body.toString())
                    if (body != null) {
                        signin_tv.text = "$body"
                        Amplify.Auth.signIn(login_email.text.toString(), login_pwd.text.toString(),
                            { result ->
                                if (result.isSignInComplete) {
                                    Log.i("AWSAuthQuickstart", "Sign in succeeded")
                                } else {
                                    Log.i("AWSAuthQuickstart", "Sign in not complete")
                                }
                            },
                            { Log.e("AuthQuickstart", "Failed to sign in", it) }
                        )
                    }
                }
            })
        }
    }
}