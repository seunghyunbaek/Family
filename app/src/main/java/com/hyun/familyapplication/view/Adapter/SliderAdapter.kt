package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.hyun.familyapplication.R
import com.hyun.familyapplication.view.Activity.ImageActivity
import pyxis.uzuki.live.rollingbanner.RollingViewPagerAdapter

class SliderAdapter(context: Context, lstImages: ArrayList<String>) :
    RollingViewPagerAdapter<String>(context, lstImages) {
    private var layoutInflater: LayoutInflater? = null
    var images: ArrayList<String>

    init {
        this.images = lstImages
    }

    override fun getView(position: Int, item: String): View {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.item_slider, null, false)
        val image = v.findViewById<View>(R.id.image_item_slider) as ImageView

        Glide.with(context).load(images[position]).into(image)
//        val vp:RollingBanner = v.findViewById(R.id.viewpager_record_activity)
//        vp.addView(v, 0)

        image.setOnClickListener {
            if (context.javaClass.simpleName.equals("RecordActivity")) {
//                val intent: Intent = Intent(context, ImageActivity::class.java)
//                intent.putExtra("uri", images[position])
//                context.startActivity(intent)

                Intent(context, ImageActivity::class.java).let {
                    it.putExtra("uri", images[position])
                    context.startActivity(it)
                }
            }
        }

        return v
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }

//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = layoutInflater!!.inflate(R.layout.item_slider, null)
//        val image = v.findViewById<View>(R.id.image_item_slider) as ImageView
//
//        Glide.with(context).load(images[position]).into(image)
////        image.setImageURI(Uri.parse(images[position]))
//        val vp = container as ViewPager
//        vp.addView(v, 0)
////        return super.instantiateItem(container, position)
//        return v
//    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}


// 기존 코드

//class SliderAdapter(private val context: Context, lstImages:List<String>) : PagerAdapter() {
//    private var layoutInflater: LayoutInflater? = null
//    var images:List<String>
//    init {
//        this.images = lstImages
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view === `object`
//    }
//
//    override fun getCount(): Int {
//        return images.size
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = layoutInflater!!.inflate(R.layout.item_slider, null)
//        val image = v.findViewById<View>(R.id.image_item_slider) as ImageView
//
//        Glide.with(context).load(images[position]).into(image)
////        image.setImageURI(Uri.parse(images[position]))
//        val vp = container as ViewPager
//        vp.addView(v, 0)
////        return super.instantiateItem(container, position)
//        return v
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val vp = container as ViewPager
//        val v = `object` as View
//        vp.removeView(v)
//    }
//}


//package com.hyun.familyapplication.view.Adapter
//
//import android.content.Context
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.viewpager.widget.PagerAdapter
//import androidx.viewpager.widget.ViewPager
//import com.bumptech.glide.Glide
//import com.hyun.familyapplication.R
//import com.hyun.familyapplication.model.RecordImage
//
//class SliderAdapter(private val context: Context, lstImages:List<RecordImage>) : PagerAdapter() {
//    private var layoutInflater: LayoutInflater? = null
//    var images:List<RecordImage>
//    init {
//        this.images = lstImages
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view === `object`
//    }
//
//    override fun getCount(): Int {
//        return images.size
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = layoutInflater!!.inflate(R.layout.item_slider, null)
//        val image = v.findViewById<View>(R.id.image_item_slider) as ImageView
//
//        Glide.with(context).load(images[position].uri).into(image)
////        image.setImageURI(Uri.parse(images[position]))
//        val vp = container as ViewPager
//        vp.addView(v, 0)
////        return super.instantiateItem(container, position)
//        return v
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val vp = container as ViewPager
//        val v = `object` as View
//        vp.removeView(v)
//    }
//}


//package com.hyun.familyapplication.view.Adapter
//
//import android.content.Context
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.viewpager.widget.PagerAdapter
//import androidx.viewpager.widget.ViewPager
//import com.hyun.familyapplication.R
//
//class SliderAdapter(private val context: Context, lstImages:List<String>) : PagerAdapter() {
//    private var layoutInflater: LayoutInflater? = null
//    var Images:List<String>
//    init {
//        this.Images = lstImages
//    }
//
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view === `object`
//    }
//
//    override fun getCount(): Int {
//        return Images.size
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = layoutInflater!!.inflate(R.layout.item_slider, null)
//        val image = v.findViewById<View>(R.id.image_item_slider) as ImageView
//
//        image.setImageURI(Uri.parse(Images[position]))
//        val vp = container as ViewPager
//        vp.addView(v, 0)
////        return super.instantiateItem(container, position)
//        return v
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val vp = container as ViewPager
//        val v = `object` as View
//        vp.removeView(v)
//    }
//}