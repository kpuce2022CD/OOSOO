package kr.ac.kpu.oosoosoo.connection

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.kakao.sdk.common.KakaoSdk
import kr.ac.kpu.oosoosoo.R

class OOSOOSOOApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // AWS Amplify
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i("AWS Amplify", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("AWS Amplify", "Could not initialize Amplify", error)
        }

        // Kakao SDK 초기화
        KakaoSdk.init(this, resources.getString(R.string.kakao_app_key))
    }
}
