package com.hyun.myapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        btnNews.setOnClickListener {
            Intent(this, NewsActivity::class.java).let{
                startActivity(it)
            }
        }

        btnGuide.setOnClickListener {
            Intent(this, GuideActivity::class.java).let {
                startActivity(it)
            }
        }

        btnMessage.setOnClickListener {
            Intent(this, TestDB::class.java).let {
                startActivity(it)
            }
        }

        btnMyLine.setOnClickListener {
            Toast.makeText(this@MainActivity, "hi", Toast.LENGTH_LONG).show()
        }
    }
}
