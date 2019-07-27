package com.hyun.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.myapplication.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        initPresenter()
    }

    abstract fun initPresenter()
}
