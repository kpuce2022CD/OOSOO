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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InterworkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interworking)

        val call = RetrofitBuilder().callInterworking  //Retrofit Call

        val callAddWishlist = RetrofitBuilder().callAddWishlist
        val callAddWatchingLog = RetrofitBuilder().callAddWatchingLog

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
        i_profileName_tv.text = platform_name + " 프로필 이름"

        //플랫폼 연동 시도
        i_login_btn.setOnClickListener {
            if (i_id_edt.length() != 0 && i_pwd_edt.length() != 0 && i_profileName_edt.length() != 0){

                // 로딩 다이얼로그 표시
                loadingDialog.show()

                var input = HashMap<String, String>()
                input["u_email"] = user_email
                input["platform"] = platform_name.toString()
                input["id"] = i_id_edt.text.toString()
                input["passwd"] = i_pwd_edt.text.toString()
                input["profile_name"] = i_profileName_edt.text.toString()
                Log.d("interworking", input["u_email"] + input["platform"] + input["id"] + input["passwd"] + input["profile_name"])

                CoroutineScope(Dispatchers.Main).launch {
                    val inter = CoroutineScope(Dispatchers.IO).launch {
                        val body = call.getInterworking(input).execute().body()
                        if (body.toString() == "null") {
                            runOnUiThread {
                                i_result_tv1.text = "서버 body = null"
                            }
                            // 로딩 다이얼로그 종료
                            loadingDialog.dismiss()
                        } else {
                            if(body == true) {
                                val intent_result = Intent()
                                runOnUiThread {
                                    i_result_tv1.text = "$platform_name 에 연동 로그인 성공!"
                                }
                                val intent_msg = platform_name

                                intent_result.putExtra("result", intent_msg)
                                setResult(Activity.RESULT_OK, intent_result)
                            } else {
                                runOnUiThread {
                                    i_result_tv1.text = "*$platform_name 에 연동 로그인 실패*"
                                }
                                // 로딩 다이얼로그 종료
                                loadingDialog.dismiss()
                            }
                        }
                    }

                    inter.join()

                    val wish_watch = CoroutineScope(Dispatchers.IO).launch {
                        var input_email = HashMap<String, String>()
                        input_email["email"] = user_email
                        input_email["platform"] = platform_name.toString()

                        callAddWishlist.callAddWishlist(input_email).enqueue(object : Callback<Boolean> {
                            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                val body_wishlist : Boolean? = response.body()
                                if (body_wishlist.toString() == "null") {
                                    i_result_tv2.text = "서버 body_wishlist = null"
                                    // 로딩 다이얼로그 종료
                                    loadingDialog.dismiss()
                                } else {
                                    if (body_wishlist == true) {
                                        i_result_tv2.text = "AddWishlist 성공"
                                    }
                                    else {
                                        i_result_tv2.text = "AddWishlist 실패"
                                        // 로딩 다이얼로그 종료
                                        loadingDialog.dismiss()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                i_result_tv2.text = "서버요청을 실패하였습니다. OTT Wishlist 연동 중 문제가 발생하였습니다."
                                // 로딩 다이얼로그 종료
                                loadingDialog.dismiss()
                            }
                        })

                        callAddWatchingLog.callAddWatchingLog(input_email).enqueue(object : Callback<Boolean> {
                            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                val body_watchinglog : Boolean? = response.body()
                                if (body_watchinglog.toString() == "null") {
                                    i_result_tv3.text = "서버 body_watchinglog = null"
                                    // 로딩 다이얼로그 종료
                                    loadingDialog.dismiss()
                                } else {
                                    if (body_watchinglog == true) {
                                        i_result_tv3.text = "AddWatchingLog 성공"

                                        // 로딩 다이얼로그 종료
                                        loadingDialog.dismiss()
                                    }
                                    else {
                                        i_result_tv3.text = "AddWatchingLog 실패"
                                        // 로딩 다이얼로그 종료
                                        loadingDialog.dismiss()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                i_result_tv3.text = "서버요청을 실패하였습니다. OTT Watching Log 연동 중 문제가 발생하였습니다."
                                // 로딩 다이얼로그 종료
                                loadingDialog.dismiss()
                            }
                        })
                    }

                    wish_watch.join()

                }

            } else {
                toast("에디트텍스트에 모든 정보 입력해주세요.").show()
            }
        }
    }
}