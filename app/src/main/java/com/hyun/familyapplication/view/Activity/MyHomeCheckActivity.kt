package com.hyun.familyapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MyHomeCheckContract
import com.hyun.familyapplication.presenter.MyHomeCheckPresenter
import kotlinx.android.synthetic.main.activity_my_home_check.*

class MyHomeCheckActivity : BaseActivity(), MyHomeCheckContract.View, View.OnClickListener {

    private lateinit var mPresenter: MyHomeCheckPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_home_check)

        mPresenter.takeView(this)

        check_guest_my_home_check.setOnClickListener(this)
        check_host_my_home_check.setOnClickListener(this)
        button_next_my_home_check.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.checkRoom(this)
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun initPresenter() {
        mPresenter = MyHomeCheckPresenter()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun createdRoom() {
        Intent(applicationContext, MyHomeActivity::class.java).let {
            startActivity(it)
        }
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.check_host_my_home_check -> {
                check_guest_my_home_check.isChecked = !check_host_my_home_check.isChecked
            }
            R.id.check_guest_my_home_check -> {
                check_host_my_home_check.isChecked = !check_guest_my_home_check.isChecked
            }
            R.id.button_next_my_home_check -> {
                if (!check_host_my_home_check.isChecked)
                    Intent(this, InvitedActivity::class.java).let{
                        startActivity(it)
                    }
                else
                    mPresenter.createRoom(this)
            }
        }
    }
}
