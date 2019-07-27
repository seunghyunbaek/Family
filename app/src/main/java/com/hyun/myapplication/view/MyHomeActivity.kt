package com.hyun.myapplication.view

import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hyun.myapplication.R
import kotlinx.android.synthetic.main.activity_my_home.*

class MyHomeActivity : AppCompatActivity() {

    private lateinit var frameLayout: FrameLayout
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_record -> {
                    val fragment = RecordFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.myhomeframe, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_calendar -> {
                    val fragment = CalendarFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.myhomeframe, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_todo -> {
                    val fragment = TodoFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.myhomeframe, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_pictures -> {
                    val fragment = PicturesFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.myhomeframe, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_family -> {
                    val fragment = FamilyFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.myhomeframe, fragment, fragment.javaClass.simpleName).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        frameLayout = findViewById(R.id.myhomeframe)
        if(savedInstanceState == null) {
            val fragment = RecordFragment()
            supportFragmentManager.beginTransaction().replace(R.id.myhomeframe, fragment, fragment.javaClass.simpleName).commit()
        }
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

}
