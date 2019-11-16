package com.hyun.familyapplication.view.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MainContract
import com.hyun.familyapplication.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : BaseActivity(), MainContract.View {

    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val date: String // 작성일
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var current = LocalDate.now()
            var formatter = DateTimeFormatter.ofPattern("MM월 dd일")
            var formatted = current.format(formatter)
            date = formatted
        } else {
            var simpleDateFormatter = SimpleDateFormat("MM월 dd일")
            var currentDate = simpleDateFormatter.format(Date())
            date = currentDate
        }

        val db = DBHelper(this)
        text_name_main.setText(db.getUser()?.name + "님 환영합니다!")
        text_day_main.setText("오늘은 " + date + " 입니다.")

        setButton()
    }

    override fun onStart() {
        super.onStart()
        if (presenter == null) {
            initPresenter()
        }
        presenter?.checkMessage(this)
//        presenter?.checkRecord(this)
    }

    override fun onDestroy() {
        presenter?.dropView()
        super.onDestroy()
    }

    fun setButton() {

        image_profile_btn.setOnClickListener {
            Intent(this@MainActivity, ProfileActivity::class.java).let {
                startActivity(it)
            }
        }

        image_signout.setOnClickListener {
            //            val mAuth = FirebaseAuth.getInstance()
//            mAuth.signOut()

//            val db = DBHelper(this)

            var dialogListener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
//                            db.deleteUser()
                            presenter?.signOut(this@MainActivity)
                        }
                    }
                }
            }

            val dialog = AlertDialog.Builder(this)
            dialog.setMessage("로그아웃 하시겠습니까?")
            dialog.setPositiveButton("예", dialogListener)
            dialog.setNegativeButton("아니오", dialogListener)
            dialog.show()
//            db.deleteUser()
        }

        btnMyHome.setOnClickListener(View.OnClickListener {
            Intent(this, MyHomeCheckActivity::class.java).let {
                startActivity(it)
            }
        })

        btnNews.setOnClickListener {
            Intent(this, NewsActivity::class.java).let {
                startActivity(it)
            }
        }

        btnGuide.setOnClickListener {
            Intent(this, GuideActivity::class.java).let {
                startActivity(it)
            }
        }

        btnMessage.setOnClickListener {
            //            Intent(this, TestDB::class.java).let {
//                startActivity(it)
//            }
            Intent(this, MessageActivity::class.java).let {
                startActivity(it)
            }
        }

        btnMyLine.setOnClickListener {
            Toast.makeText(this@MainActivity, "hi", Toast.LENGTH_LONG).show()
            Intent(this, WriteTodoActivity::class.java).let {
                startActivity(it)
            }
        }

        btnFeedBack.setOnClickListener {
            Intent(this, ClientActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        finishAffinity()

        var dialogListener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
//                            db.deleteUser()
                        finishAffinity()
                    }
                }
            }
        }

        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("앱을 종료하시겠습니까?")
        dialog.setPositiveButton("예", dialogListener)
        dialog.setNegativeButton("아니오", dialogListener)
        dialog.show()
    }

    override fun initPresenter() {
        presenter = MainPresenter()
        presenter?.takeView(this)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun checkMessage(count: Int) {
        if (count == 0) {
            textMessageCount.visibility = View.INVISIBLE
        } else {
            textMessageCount.visibility = View.VISIBLE
            textMessageCount.text = count.toString()
        }
    }

    override fun checkRecord(count: Int) {
        if (count < 0) {
            textRecordCount.visibility = View.INVISIBLE
        } else {
            textRecordCount.visibility = View.VISIBLE
            textRecordCount.text = count.toString()
        }
    }

    override fun signInActivity() {
        Intent(this, SignInActivity::class.java).let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
        }
    }
}
