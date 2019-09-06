package com.hyun.familyapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        image_profile_back.setOnClickListener {
            // 뒤로가기
            onBackPressed()
        }

        text_profile_save.setOnClickListener {
            // 프로필 저장하기
        }


    }
}
