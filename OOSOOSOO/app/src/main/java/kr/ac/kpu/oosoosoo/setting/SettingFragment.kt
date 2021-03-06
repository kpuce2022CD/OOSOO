package kr.ac.kpu.oosoosoo.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kr.ac.kpu.oosoosoo.R
import kr.ac.kpu.oosoosoo.connection.RetrofitBuilder
import kr.ac.kpu.oosoosoo.login.LoginActivity
import kr.ac.kpu.oosoosoo.login.SelectIwActivity
import kr.ac.kpu.oosoosoo.user.UserInfoActivity
import kr.ac.kpu.oosoosoo.user.UserReviewActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_setting, container, false)



        view.btn_settings_mypage.setOnClickListener {
            requireContext().startActivity<UserInfoActivity>()
        }

        view.btn_settings_review.setOnClickListener {
            requireContext().startActivity<UserReviewActivity>()
        }

        //OTT ????????? ?????? ??????
        view.btn_settings_interworking.setOnClickListener {

            Amplify.Auth.fetchAuthSession(
                {
                    if (it.isSignedIn) {
                        Log.d("AWS Auth E-Mail", Amplify.Auth.currentUser.username)
                        requireContext().startActivity<SelectIwActivity>(
                            "email" to Amplify.Auth.currentUser.username
                        )
                    } else {
                        requireContext().toast("????????? ??? ??????????????????!")
                    }
                },
                { error -> Log.e("AWS AmplifyQuickstart", "Failed to fetch auth session", error) }
            )
        }

        view.btn_settings_guide.setOnClickListener {

        }

        //?????????????????? ??? ?????????
        view.btn_settings_logout.setOnClickListener {
            val options = AuthSignOutOptions.builder()
                .globalSignOut(true)
                .build()
            Amplify.Auth.signOut(options,
                { Log.i("AuthQuickstart", "Signed out globally") },
                { Log.e("AuthQuickstart", "Sign out failed", it) }
            )
            activity?.finishAffinity()
            requireContext().startActivity<LoginActivity>()
        }

        view.btn_settings_withdraw.setOnClickListener {
            val call_delete = RetrofitBuilder().deleteUser

            Amplify.Auth.deleteUser(
                { Log.i("AuthQuickStart", "Delete user succeeded") },
                { Log.e("AuthQuickStart", "Delete user failed with error", it) }
            )

            var input = HashMap<String, String>()
            input["email"] = Amplify.Auth.currentUser.username
            Log.d("user_withdraw", "${input["email"]}")

            call_delete.deleteUser(input).enqueue(object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable){
                    Log.d("user_withdraw", t.message.toString())
                    Toast.makeText(requireContext(), "??????????????? ?????????????????????. ????????? ????????? ??????????????????.", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<String>, response: Response<String>){
                    val body: String? = response.body()
                    Log.d("user_withdraw", "?????? ??????")
                    if(body == "Success"){
                        Toast.makeText(requireContext(), "???????????? ??????", Toast.LENGTH_LONG).show()
                        activity?.finishAffinity()
                        requireContext().startActivity<LoginActivity>()
                    } else {
                        Toast.makeText(requireContext(), "???????????? ??????", Toast.LENGTH_LONG).show()
                    }

                }
            })
        }

        Log.d("setting", "?????? ??????")
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                SettingFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onDestroy() {
        Log.d("setting", "?????? ??????")
        super.onDestroy()
    }
}