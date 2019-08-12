package com.hyun.myapplication.view.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.view.Adapter.GuideAdapter
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        val guideRecyclerView = findViewById<RecyclerView>(R.id.guideRecyclerView)
        guideRecyclerView.layoutManager = LinearLayoutManager(this)
        guideRecyclerView.adapter = GuideAdapter()

        btnBackGuide.setOnClickListener { onBackPressed() }
    }
}
