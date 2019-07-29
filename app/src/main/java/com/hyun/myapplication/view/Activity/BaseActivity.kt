package com.hyun.myapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.myapplication.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        initPresenter()
    }

    // view와 상호작용할 Presenter를 주입하기위한 함수
    abstract fun initPresenter()
}
