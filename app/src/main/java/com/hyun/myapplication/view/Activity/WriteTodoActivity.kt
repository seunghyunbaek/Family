package com.hyun.myapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.myapplication.R
import kotlinx.android.synthetic.main.activity_write_todo.*

class WriteTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_todo)


        btnBackWT.setOnClickListener { onBackPressed() }

    }
}
