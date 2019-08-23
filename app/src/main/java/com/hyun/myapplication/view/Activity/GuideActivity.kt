package com.hyun.myapplication.view.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.view.Adapter.GuideAdapter
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    // Private, non-static : m
    // Private, static : s
    // Other fields : lowerCaseletter
    // Static fields (constants) : ALL_CAPS_WITH_UNDERSCORES

    // 1. Constants
    // 2. Fields
    // 3. Constructors
    // 4. Override methods and callbacks ( public or private )
    // 5. Public methods
    // 6. Private methods
    // 7. Inner classes or interfaces

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        val guideRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_guide)
        guideRecyclerView.layoutManager = LinearLayoutManager(this)
        guideRecyclerView.adapter = GuideAdapter()

        image_back_guide.setOnClickListener { onBackPressed() }
    }
}
