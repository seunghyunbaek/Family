package com.hyun.familyapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.News
import com.hyun.familyapplication.view.Adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_news)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.adapter = NewsAdapter(this, getNews())

        image_back_news.setOnClickListener { onBackPressed() }
    }

    fun getNews():ArrayList<News>{
        var newsList = ArrayList<News>()

        newsList.add(News("이용해 주셔서 감사합니다.",
            "2019.11.16",
            "안녕하세요. 운영팀입니다.\n" +
                "나름 열심히 준비한 서비스를 통해 여러분을 만나뵙게 되어 반갑습니다.\n\n" +
                    "사용하시다가 궁금하시거나 개선이 필요한 부분은 메인화면의 사용자의견을 통해 의견주시면 더 나은 서비스를 만드는데 많은 도움이 될 것 같습니다.\n\n" +
                    "감사합니다."))

        return newsList
    }
}
