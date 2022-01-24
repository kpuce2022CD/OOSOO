package com.example.oosoosoo

import RetrofitBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_contents.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents)

        val call = RetrofitBuilder().callContents

        call.getContentInfo().enqueue(object : Callback<List<ContentInfo>> {

            override fun onResponse(call: Call<List<ContentInfo>>, response: Response<List<ContentInfo>>) {
                val contents = response.body()

                Log.d("movie","통신 성공")

                if (contents != null) {
                    for (content in contents) {
                        Log.d("movie", content.toString())
                        content_id.text = content.c_id.toString()
                        content_genre.text = content.c_genre.toString()
                        content_name.text = content.c_name.toString()
                        content_release.text = content.c_release_date.toString()
                        content_cast.text = content.c_cast.toString()
                        content_platform.text = content.c_id.toString()
                        content_information.text = content.c_id.toString()
                        content_url.text = content.c_id.toString()
                        content_number.text = content.c_id.toString()


                    }
                }
            }

            override fun onFailure(call: Call<List<ContentInfo>>, t: Throwable) {
                Log.d("movie", t.message.toString())
            }
        })

        btn_contents_back.setOnClickListener{
            finish()
        }
    }


}