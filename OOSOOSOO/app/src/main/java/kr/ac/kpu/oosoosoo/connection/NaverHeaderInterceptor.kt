package kr.ac.kpu.oosoosoo.connection

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NaverHeaderInterceptor constructor(private val token: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer $token"
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}