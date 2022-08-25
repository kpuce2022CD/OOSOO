package kr.ac.kpu.oosoosoo.login


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_interworking.*
import kotlinx.coroutines.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.dialog.LoadingDialog
import org.jetbrains.anko.toast
import java.io.IOException


class InterworkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interworking)

        val call = RetrofitBuilder().callInterworking  //Retrofit Call

        val loadingDialog = LoadingDialog(this@InterworkingActivity)

        //돌아가기 버튼
        iw_back_btn.setOnClickListener {
            finish()
        }

        //인텐트로 넘어온 값으로 화면 생성
        val platform_name = intent.getStringExtra("platform")
        val user_email = Amplify.Auth.currentUser.username

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
        i_profileName_tv.text = platform_name + " 프로필명"

        //플랫폼 연동 시도
        i_login_btn.setOnClickListener {
            if (i_id_edt.length() != 0 && i_pwd_edt.length() != 0 && i_profileName_edt.length() != 0){
                // 연동 로그인 정보
                val input = HashMap<String, String>()
                input["u_email"] = user_email
                input["platform"] = platform_name.toString()
                input["id"] = i_id_edt.text.toString()
                input["passwd"] = i_pwd_edt.text.toString()
                input["profile_name"] = i_profileName_edt.text.toString()
                Log.d("interworking", input["u_email"] + input["platform"] + input["id"] + input["passwd"] + input["profile_name"])



                // 로딩 다이얼로그 표시
                loadingDialog.show()

                // DB에 연동 정보 추가
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val body = call.getInterworking(input).execute().body()
                        if (body.toString() == "null") Log.d("Interworking", "서버 body = null")
                        else {
                            if(body != true) Log.d("Interworking", "$platform_name 에 연동 로그인 실패")
                            else {
                                val intent_result = Intent()
                                Log.d("Interworking", "$platform_name 에 연동 로그인 성공!")

                                // 플랫폼 로그인 정보
                                val input_email = HashMap<String, String>()
                                input_email["email"] = user_email
                                input_email["platform"] = platform_name.toString()

                                // 플랫폼 연동
                                runBlocking {
                                    launch { wishlistCall(input_email) }
                                    launch { watchingLogCall(input_email) }
                                }

                                intent_result.putExtra("result", platform_name)
                                setResult(Activity.RESULT_OK, intent_result)
                            }
                        }
                        // 로딩 다이얼로그 중지
                        loadingDialog.dismiss()
                        finish()
                    }
                    catch (except : IOException) { Log.d("Interworking", except.toString()) }
                    catch (except : RuntimeException) { Log.d("Interworking", except.toString()) }
                }
            }
            else toast("에디트텍스트에 모든 정보를 입력해주세요.").show()
        }

    }

    suspend fun wishlistCall(input_email: HashMap<String, String>) {
        val callAddWishlist = RetrofitBuilder().callAddWishlist

        val body_wishlist = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            callAddWishlist.callAddWishlist(input_email).execute().body()
        }

        if (body_wishlist.toString() == "null") {
            Log.d("Interworking", "서버 body_wishlist = null")
        } else {
            if (body_wishlist == true) Log.d("Interworking", "AddWishlist 성공")
            else Log.d("Interworking", "AddWishlist 실패")
        }
    }

    suspend fun watchingLogCall(input_email: HashMap<String, String>) {
        val callAddWatchingLog = RetrofitBuilder().callAddWatchingLog

        val body_watchinglog = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            callAddWatchingLog.callAddWatchingLog(input_email).execute().body()
        }

        if (body_watchinglog.toString() == "null") {
            Log.d("Interworking", "서버 body_watchinglog = null")
        } else {
            if (body_watchinglog == true) Log.d("Interworking", "AddWatchingLog 성공")
            else Log.d("Interworking", "AddWatchingLog 실패")
        }
    }

}