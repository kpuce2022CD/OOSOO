package kr.ac.kpu.oosoosoo.user

import android.os.Bundle
import android.util.Log
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_user_info.*
import kr.ac.kpu.oosoosoo.BaseActivity
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.home.HomeActivity
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoActivity : BaseActivity(TransitionMode.NONE) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val call = RetrofitBuilder().callUserInfo
        var input = HashMap<String, String>()

        input["email"] = Amplify.Auth.currentUser.username
        val userInfo : UserInfo //유저 정보

        call.getUserInfo(input).enqueue(object : Callback<UserInfo> {

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                Log.d("UserInfo", "통신 성공")

                if (userInfo != null) {
                    userinfo_email_text.text = userInfo.email
                    userinfo_name_text.append(userInfo.name)
                    userinfo_nickname_text.text =userInfo.nickname
                    userinfo_phone_text.append(userInfo.phone_number)
                    userinfo_job_text.append(userInfo.job)
                    userinfo_birthday_text.append(userInfo.birthday)
                    if(userInfo.gender == 1) {
                        userinfo_gender_imageView.setImageDrawable(getDrawable(R.drawable.ic_male_16))
                    } else {
                        userinfo_gender_imageView.setImageDrawable(getDrawable(R.drawable.ic_female_16))
                    }
                    userinfo_overview_text.append(userInfo.overview)
                }

            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("User Info Fail", t.message.toString())
            }
        })

        btn_userinfo_back.setOnClickListener {
            finish()
        }

        btn_update_user.setOnClickListener {
            startActivity<UserUpdateActivity>()
        }
    }
}