package com.hyun.myapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hyun.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButton()
    }

    fun setButton() {
        btnMyHome.setOnClickListener(View.OnClickListener {
            Intent(this, MyHomeActivity::class.java).let {
                startActivity(it)
            }
        })
    }
}
