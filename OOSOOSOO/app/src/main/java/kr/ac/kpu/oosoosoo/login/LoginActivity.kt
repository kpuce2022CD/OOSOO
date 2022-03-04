package kr.ac.kpu.oosoosoo.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amplifyframework.core.Amplify
import com.google.gson.JsonObject
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.android.synthetic.main.activity_interworking.*
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val call = RetrofitBuilder().callLogin  //Retrofit Call
        val naver_call = RetrofitBuilder().NaverProfile // Naver Retrofit

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

        btn_kakao.setOnClickListener {
            // 카카오계정으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error != null) {
                    Log.e("Kakao Login", "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i("Kakao Login", "로그인 성공 ${token.accessToken}")
                    // 사용자 정보 요청 (기본)
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("Kakao Login", "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i("Kakao Login", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n성별: ${user.kakaoAccount?.gender}")

                            var input = HashMap<String, String>()
                            input["email"] = user.kakaoAccount?.email.toString()
                            input["pwd"] = "kakao" + user.id.toString()

                            call.getSignIn(input).enqueue(object : Callback<Boolean>{
                                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                    Log.d("Kakao Login", t.message.toString())
                                    signin_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                                    startActivity<SignupActivity>(
                                        "email" to input["email"],
                                        "pwd" to input["pwd"],
                                        "nickname" to user.kakaoAccount?.profile?.nickname.toString(),
                                        "gender" to user.kakaoAccount?.gender.toString()
                                    )
                                }

                                override fun onResponse(call: Call<Boolean>, response: Response<Boolean> ) {
                                    val body : Boolean? = response.body()
                                    Log.d("Kakao Login", "통신 성공")
                                    signin_tv.text = ""

                                    Log.d("Kakao Login", body.toString())
                                    if (body != null) {
                                        signin_tv.text = "$body"
                                        Amplify.Auth.signIn(login_email.text.toString(), login_pwd.text.toString(),
                                            { result ->
                                                if (result.isSignInComplete) {
                                                    Log.i("AWSAuth + Kakao", "Sign in succeeded")
                                                } else {
                                                    Log.i("AWSAuth + Kakao", "Sign in not complete")
                                                }
                                            },
                                            { Log.e("AWSAuth + Kakao", "Failed to sign in", it) }
                                        )
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
        naver_login_btn.setOnClickListener {
            val naver_client_id = "cWHgfLAMEk_ytHCxktHu"
            val naver_client_secret = "kJidBtWGGT"
            val naver_client_name = "OOSOOSOO 네이버 로그인"

            NaverIdLoginSDK.initialize(this, naver_client_id, naver_client_secret, naver_client_name)

            oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    Log.d("naver_login", "네이버 로그인 성공" )
                    //var RefreshToken = NaverIdLoginSDK.getRefreshToken().toString()
                    var AccessToken = NaverIdLoginSDK.getAccessToken().toString()
                    var State = NaverIdLoginSDK.getState().toString()

                    naver_call.getNaverProfile(AccessToken).enqueue(object : Callback<List<String>> {
                        override fun onFailure(call: Call<List<String>>, t: Throwable) {
                            Log.d("naver_login", "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요." )
                        }

                        override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {

                            /*val jsonObject = JSONTokener(response.toString()).nextValue() as JSONObject

                            val n_email = jsonObject.getString("response/email")
                            val n_nickname = jsonObject.getString("response/nickname")
                            val n_gender = jsonObject.getString("response/gender")
                            val n_birthyear = jsonObject.getString("response/birthyear")
                            val n_birthday = jsonObject.getString("response/birthday")
                            val n_phoneNum = jsonObject.getString("response/moblie")

                            Log.d("naver_login", "이메일 $n_email\n닉네임 $n_nickname\n성별 $n_gender\n 생년월일 $n_birthyear $n_birthday \n전화번호 $n_phoneNum")
                        */}
                    })

                    Toast.makeText(this@LoginActivity,"$State",Toast.LENGTH_LONG).show()

                    // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가

                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Toast.makeText(this@LoginActivity,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }
            NaverIdLoginSDK.authenticate(this@LoginActivity, oauthLoginCallback)
        }

        n_logout_btn.setOnClickListener {
            NaverIdLoginSDK.logout()
        }

    }
}

private fun NidOAuthLogin.callDeleteTokenApi() {
    TODO("Not yet implemented")
}
