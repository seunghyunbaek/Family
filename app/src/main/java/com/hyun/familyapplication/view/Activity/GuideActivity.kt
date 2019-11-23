package com.hyun.familyapplication.view.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.Guide
import com.hyun.familyapplication.view.Adapter.GuideAdapter
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
        guideRecyclerView.adapter = GuideAdapter(this, getGuide())

        image_back_guide.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }

    fun getGuide(): Array<Guide> {
        var guideList: Array<Guide> = arrayOf(
            Guide(
                "#1. 로그인 관리",
                "로그인 계정을 로그아웃 시킬 수 있어요.",
                "로그아웃 버튼을 이용하여 로그아웃 할 수 있습니다. \n" +
                        "로그아웃 하더라도 동일한 계정으로 로그인하면 그대로 불러올 수 있습니다. \n\n" +
                        "그런만큼 해당 로그인 계정은 반드시 기억해야겠죠?",
                R.drawable.mainactivity_exit
            ),
            Guide(
                "#2. 건의하기",
                "불편한 점이나 건의사항을 보낼 수 있어요.",
                "앱을 사용하는 동안에 생긴 문제점이나 개선할 점을 개발자에게 직접 메일을 보낼 수 있습니다. \n",
                R.drawable.mainactivity_comment
            )
        )
        return guideList
    }

//    fun getGuide():Array<Guide> {
//        var guideList:Array<Guide> = arrayOf(
//            Guide("#1. 로그인 관리",
//                "로그인 계정을 로그아웃 시킬 수 있어요.",
//                arrayOf("로그아웃 버튼을 이용하여 로그아웃 할 수 있습니다. \n " +
//                        "로그아웃 하더라도 동일한 계정으로 로그인하면 그대로 불러올 수 있습니다. \n\n" +
//                        "그런만큼 해당 로그인 계정은 반드시 기억해야겠죠?"),
//                arrayOf(R.drawable.mainactivity_exit)),
//            Guide("#2. 건의하기",
//                "불편한 점이나 건의사항을 보낼 수 있어요.",
//                arrayOf("앱을 사용하는 동안에 생긴 문제점이나 개선할 점을 개발자에게 직접 메일을 보낼 수 있습니다. \n"),
//                arrayOf(R.drawable.mainactivity_comment))
//        )
//
//        return guideList
//    }
}
