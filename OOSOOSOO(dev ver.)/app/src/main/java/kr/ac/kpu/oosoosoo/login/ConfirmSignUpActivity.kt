package kr.ac.kpu.oosoosoo.login

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_confirm_sign_up.*
import kr.ac.kpu.oosoosoo.R
import org.jetbrains.anko.startActivity

class ConfirmSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up)

        val email = intent.getStringExtra("email")

        btn_confirm.setOnClickListener {
            Amplify.Auth.confirmSignUp(
                email!!, edt_confirm.text.toString(),
                { result ->
                    if (result.isSignUpComplete) {
                        Log.i("AWSAuthQuickstart", "Confirm signUp succeeded")
                        runOnUiThread {
                            tv_confirm.text = "인증 성공!"
                            tv_confirm.setTextColor(Color.BLUE)
                            btn_confirm_next.isEnabled = true
                        }
                    } else {
                        Log.i("AWSAuthQuickstart","Confirm sign up not complete")
                        runOnUiThread {
                            tv_confirm.text = "인증 실패"
                            tv_confirm.setTextColor(Color.RED)
                        }
                    }
                },
                { Log.e("AWSAuthQuickstart", "Failed to confirm sign up", it) }
            )
        }

        btn_confirm_next.setOnClickListener {
            startActivity<SelectIwActivity>(
                "email" to email
            )
        }
    }
}