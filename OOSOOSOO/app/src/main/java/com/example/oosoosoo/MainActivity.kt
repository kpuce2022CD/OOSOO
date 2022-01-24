package com.example.oosoosoo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main.setOnClickListener { view ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_contents -> startActivity(Intent(this, ContentsActivity::class.java))
                R.id.radio_review -> startActivity(Intent(this, ReviewActivity::class.java))
            }
        }
    }


}