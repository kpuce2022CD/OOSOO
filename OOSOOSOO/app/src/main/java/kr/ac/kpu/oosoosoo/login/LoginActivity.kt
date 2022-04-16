package kr.ac.kpu.oosoosoo.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amplifyframework.core.Amplify
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.android.synthetic.main.activity_interworking.*
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.NaverHeaderInterceptor
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.home.HomeActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                                    startActivity<HomeActivity>()
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
            NaverIdLoginSDK.showDevelopersLog(true) // 네이버 로그 보게 나중에 지워야함
            NaverIdLoginSDK.initialize(this, "cWHgfLAMEk_ytHCxktHu", "kJidBtWGGT", "OOSOOSOO 네이버 로그인")
            // client_id, client_secret, client_name

            oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    val accessToken = NaverIdLoginSDK.getAccessToken().toString()

                    val url = "https://openapi.naver.com/v1/nid/me"

                    val naverRequest = Request.Builder()
                        .url(url)
                        .get()
                        .build()

                    val naverClient =  OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .addInterceptor(NaverHeaderInterceptor(accessToken))
                        .build()

                    naverClient.newCall(naverRequest).enqueue(object : okhttp3.Callback {
                        override fun onFailure(call: okhttp3.Call, e: IOException) {
                            Log.d("naver_login_call", "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요." )
                        }
                        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                            val profile = response.body?.string()
                            val call = RetrofitBuilder().callLogin  //Retrofit Call

                            val naverProfile = NaverProfileInfo(
                                resultcode = JSONObject(profile).getString("resultcode"),
                                message = JSONObject(profile).getString("message"),
                                response = JSONObject(profile).getJSONObject("response")
                            )

                            var input = HashMap<String, String>()
                            input["email"] = naverProfile.response.getString("email")
                            input["pwd"] = naverProfile.response.getString("id")

                            call.getSignIn(input).enqueue(object : Callback<Boolean> {
                                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                    Log.d("naver Login", t.message.toString())
                                    signin_tv.text = "서버요청을 실패하였습니다. 입력한 정보를 확인해주세요."
                                }
                                override fun onResponse(call: Call<Boolean>, response: Response<Boolean> ) {
                                    val body : Boolean? = response.body()
                                    Log.d("naver Login", "통신 성공")
                                    signin_tv.text = ""

                                    Log.d("naver Login", body.toString())
                                    if (body != null) { // DB에 로그인 요청 리턴값이 null이 아닐 때
                                        signin_tv.text = "$body"
                                        if (body == true){ // DB에 유저 정보가 있을 경우
                                            Amplify.Auth.signIn(naverProfile.response.getString("email"), naverProfile.response.getString("id"),
                                                { result ->
                                                    if (result.isSignInComplete) {
                                                        Log.i("AWSAuth + naver", "Sign in succeeded")
                                                    } else {
                                                        Log.i("AWSAuth + naver", "Sign in not complete")
                                                    }
                                                },
                                                { Log.e("AWSAuth + naver", "Failed to sign in", it) }
                                            )
                                        } else { // 신규 회원인 경우
                                            var gender = naverProfile.response.getString("gender")
                                            var gender_boolean = false
                                            if(gender == "M"){
                                                gender_boolean = true
                                            } else if (gender == "F"){
                                                gender_boolean = false
                                            }
                                            startActivity<SignupActivity>(
                                                "name" to naverProfile.response.getString("name"),
                                                "email" to input["email"],
                                                "pwd" to input["pwd"],
                                                "nickname" to naverProfile.response.getString("nickname"),
                                                //"gender" to gender_boolean.toString(),
                                                "phoen_num" to naverProfile.response.getString("mobile"),
                                                "birthday" to naverProfile.response.getString("birthyear")
                                            )
                                        }
                                    }
                                }
                            })
                        }
                    })
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
        /*네이버 로그아웃 버튼 나중에 로그아웃 페이지에 넣어야됨
        n_logout_btn.setOnClickListener {
            NaverIdLoginSDK.logout()
        }*/

    }
}