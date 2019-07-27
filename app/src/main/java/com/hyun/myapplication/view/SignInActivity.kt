package com.hyun.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hyun.myapplication.R
import com.hyun.myapplication.contract.SignInContract
import com.hyun.myapplication.presenter.SignInPresenter
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(), SignInContract.View {

    private lateinit var signinPresenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signinPresenter.takeView(this)

        setButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        signinPresenter.dropView()
    }

    fun setButton() {
        btnSignInWithGoogle.setOnClickListener {
            if (signinPresenter.doLogin("a1@gmail.com", "123")) {
                Intent(this, MainActivity::class.java).let {
                    startActivity(it)
                }
            }

        }
    }

    override fun initPresenter() {
        signinPresenter = SignInPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@SignInActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar2.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar2.visibility = View.GONE
    }
}
