package com.hyun.familyapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.hyun.familyapplication.R
import com.hyun.familyapplication.view.Adapter.SliderAdapter
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        button_back_record_activity.setOnClickListener {
            onBackPressed()
        }

        val i = intent

        val b = i.extras

        if(b.getStringArrayList("imglist") != null) {
//            println("11111111111111111111111111111111111111111111111111")
//            println(b.getStringArrayList("imglist"))
            val name = b.getString("name")
            val content = b.getString("content")
            val date = b.getString("date")
            val imglist = b.getStringArrayList("imglist")

            text_name_record_activity.text = name
            text_content_record_activity.text = content
            text_date_record_activity.text = date

            val adapter = SliderAdapter(this,this, imglist)
            viewpager_record_activity.setAdapter(adapter)
//            viewpager_record_activity.adapter = adapter
        } else {
            val name = b.getString("name")
            val content = b.getString("content")
            val date = b.getString("date")

            text_name_record_activity.text = name
            text_content_record_activity.text = content
            text_date_record_activity.text = date
        }

    }

    override fun onResume() {
        super.onResume()
        button_back_record_activity.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        button_back_record_activity.visibility = View.GONE
        text_date_record_activity.visibility = View.GONE
        super.onBackPressed()
    }
}
