package com.hyun.familyapplication.view.Activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.activity_guide_detail.*

class GuideDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_detail)

        val i = intent
        val title = i.extras.getString("title")
        val subtitle = i.extras.getString("subtitle")
        val description = i.extras.getString("description")
        val image = i.extras.getInt("images")

        text_guide_detail_title.text = title
        text_guide_detail_subtitle.text = subtitle

        val container = container_guide_detail

        text_desc_guide_detail.text = description
        image_guide_detail.setImageResource(image)

//        val textView = TextView(this)
//        textView.text = "Sample Text"
//        textView.setTextSize(10f)
//        textView.setTextColor(Color.BLACK)
//        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        textView.layoutParams = lp
//        container.addView(textView)

    }
}



//package com.hyun.familyapplication.view.Activity
//
//import android.graphics.Color
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import com.hyun.familyapplication.R
//import kotlinx.android.synthetic.main.activity_guide_detail.*
//
//class GuideDetailActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_guide_detail)
//
//        val i = intent
//        val title = i.extras.getString("title")
//        val subtitle = i.extras.getString("subtitle")
//        val description = i.extras.getStringArray("description")
//        val image = i.extras.getIntArray("images")
//
//
//        text_guide_detail_title.text = title
//        text_guide_detail_subtitle.text = subtitle
//
//        val container = container_guide_detail
//
//        val tvlist = ArrayList<TextView>()
//        val imglist = ArrayList<ImageView>()
//
//        val size = description.size
//        var cnt = 0
//        while(cnt < size) {
//            val tv = TextView(this)
//            tv.text = description[cnt]
//            val img = ImageView(this)
//            img.setImageResource(R.drawable.mainactivity_exit)
////            img.setImageDrawable(R.drawable.mainactivity_exit)
//            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            tv.layoutParams = lp
//            img.layoutParams = lp
//
//            container.addView(tv)
//            container.addView(img)
////            tvlist.add(tv)
////            imglist.add(img)
//            cnt++
//        }
//
//
////        val textView = TextView(this)
////        textView.text = "Sample Text"
////        textView.setTextSize(10f)
////        textView.setTextColor(Color.BLACK)
////        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
////        textView.layoutParams = lp
////        container.addView(textView)
//
//    }
//}
