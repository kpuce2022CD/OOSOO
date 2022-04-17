package kr.ac.kpu.oosoosoo.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.home.HomeActivity
import kr.ac.kpu.oosoosoo.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user_info, container, false)

        view.btn_userinfo_back.setOnClickListener {
            val homeActivity = activity as HomeActivity
            homeActivity.removeFragment(this)
            homeActivity.replaceFragment(HomeFragment())
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val call = RetrofitBuilder().callUserInfo
        var input = HashMap<String, String>()

        input["email"] = Amplify.Auth.currentUser.username
        val userInfo : UserInfo //유저 정보

        call.getUserInfo(input).enqueue(object : Callback<UserInfo> {

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                Log.d("UserInfo", "통신 성공")

                if (userInfo != null) {
                    userinfo_email_text.append(userInfo.email)
                    userinfo_name_text.append(userInfo.name)
                    userinfo_nickname_text.append(userInfo.nickname)
                    userinfo_phone_text.append(userInfo.phone_number)
                    userinfo_job_text.append(userInfo.job)
                    userinfo_birthday_text.append(userInfo.birthday)
                    if(userInfo.gender == 1) {
                        userinfo_gender_text.append("남자")
                    } else {
                        userinfo_gender_text.append("여자")
                    }
                    userinfo_overview_text.append(userInfo.overview)
                }

            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("User Info Fail", t.message.toString())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        parentFragmentManager.beginTransaction().remove(this)
    }
}