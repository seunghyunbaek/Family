package com.hyun.familyapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val i = intent
        val title = i.extras.getString("title")
        val subtitle = i.extras.getString("subtitle")
        val description = i.extras.getString("description")
//        val image = i.extras.getInt("images")

        text_news_detail_title.text = title
        text_news_detail_subtitle.text = subtitle

        val container = container_news_detail

        text_desc_news_detail.text = description
//        image_news_detail.setImageResource(image)
    }
}
