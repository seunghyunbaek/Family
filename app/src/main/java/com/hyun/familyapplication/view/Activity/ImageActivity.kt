package com.hyun.familyapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val uri = intent.getStringExtra("uri")
        Glide.with(this).load(uri).into(image_image)

        button_back_image.setOnClickListener {
            onBackPressed()
        }

        image_image.setOnClickListener {
//            onBackPressed()
        }
    }

    override fun onBackPressed() {
//        button_back_image.visibility = View.GONE
        super.onBackPressed()
    }
}
