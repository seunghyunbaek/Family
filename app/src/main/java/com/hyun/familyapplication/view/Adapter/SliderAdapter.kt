package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.hyun.familyapplication.R

class SliderAdapter(private val context: Context, lstImages:List<String>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    var Images:List<String>
    init {
        this.Images = lstImages
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return Images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.item_slider, null)
        val image = v.findViewById<View>(R.id.image_item_slider) as ImageView

        image.setImageURI(Uri.parse(Images[position]))
        val vp = container as ViewPager
        vp.addView(v, 0)
//        return super.instantiateItem(container, position)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}