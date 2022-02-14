package kr.ac.kpu.oosoosoo

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify

class AWSAmplifyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.configure(applicationContext)
            Log.i("AWS Amplify", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("AWS Amplify", "Could not initialize Amplify", error)
        }
    }
}
