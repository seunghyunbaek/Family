package com.hyun.familyapplication.view.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_write_message.*

class WriteMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_message)

        linear_group_find_user.setOnClickListener {
            Intent(this, Find2Activity::class.java).let {
                startActivityForResult(it, 1)
            }
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }
}
