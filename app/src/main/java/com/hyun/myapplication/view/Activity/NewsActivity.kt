package com.hyun.myapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.view.Adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_news)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = NewsAdapter()

        image_back_news.setOnClickListener { onBackPressed() }
    }
}
