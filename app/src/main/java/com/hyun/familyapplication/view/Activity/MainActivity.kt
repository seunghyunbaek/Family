package com.hyun.familyapplication.view.Activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.transition.Explode
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

        profileimg.setOnClickListener {
            val intent = Intent(this, ClientActivity::class.java)
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    this@MainActivity, profileimg,
                    ViewCompat.getTransitionName(profileimg)!!
                )
            startActivity(intent, options.toBundle())
        }

//        btnFriends.setOnClickListener {
//            btnFriends.animate()
//                .rotationY(360f) // 회전 각도
//                .rotationYBy(180f) // 180도씩 회전
//                .setDuration(500) // 애니메이션 시간
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationRepeat(animation: Animator?) {
//                        super.onAnimationRepeat(animation)
//                    }
//
//                    override fun onAnimationEnd(animation: Animator?) {
//                        super.onAnimationEnd(animation)
//                    }
//
//                    override fun onAnimationCancel(animation: Animator?) {
//                        super.onAnimationCancel(animation)
//                    }
//
//                    override fun onAnimationPause(animation: Animator?) {
//                        super.onAnimationPause(animation)
//                    }
//
//                    override fun onAnimationStart(animation: Animator?) {
//                        super.onAnimationStart(animation)
//                    }
//
//                    override fun onAnimationResume(animation: Animator?) {
//                        super.onAnimationResume(animation)
//                    }
//                })
//                .start()
//        }

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
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//                overridePendingTransition(R.anim.fadeout, R.anim.fadein)
            }
        })

        btnNews.setOnClickListener {
            Intent(this, NewsActivity::class.java).let {
                startActivity(it)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//                overridePendingTransition(R.anim.slidedown, R.anim.slideup)
            }
        }

        btnGuide.setOnClickListener {
            Intent(this, GuideActivity::class.java).let {
                startActivity(it)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
        }

        btnMessage.setOnClickListener {
            //            Intent(this, TestDB::class.java).let {
//                startActivity(it)
//            }
            Intent(this, MessageActivity::class.java).let {
                startActivity(it)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
        }

        btnMyLine.setOnClickListener {
            Toast.makeText(this@MainActivity, "hi", Toast.LENGTH_LONG).show()
            Intent(this, WriteTodoActivity::class.java).let {
                startActivity(it)
            }
        }

        btnFeedBack.setOnClickListener {
//            Intent(this, ClientActivity::class.java).let {
//                startActivity(it)
//            }
            val email:Intent  = Intent(Intent.ACTION_SEND);
            email.setType("text/plain");
//            val eee:Array<String> = arrayOf("back947@naver.com")
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf("back947@naver.com"));
            email.putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + " " + "report" + ">");
            email.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + "1.0" + "\n모델명 (Device):${Build.MODEL}\n안드로이드 OS (Android OS):${Build.VERSION.RELEASE}\n문의내용 (Content):\n");
            email.setType("message/rfc822");
            startActivity(email);
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

        val dialogbuilder = AlertDialog.Builder(this)
        dialogbuilder.setMessage("앱을 종료하시겠습니까?")
        dialogbuilder.setPositiveButton("예", dialogListener)
        dialogbuilder.setNegativeButton("아니오", dialogListener)
//        dialogbuilder.show()

        val alertDialog = dialogbuilder.create()
        alertDialog.window.attributes.windowAnimations = R.anim.slidedown
        alertDialog.show()
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
