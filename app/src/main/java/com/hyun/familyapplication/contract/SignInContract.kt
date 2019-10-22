package com.hyun.familyapplication.contract

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface SignInContract {
    // Signin View가 구현해야할 interface
    interface View : BaseView {
        // 데이터를 받아서 정제하는 동안 보일 ProgressBar 관리
        fun showLoading() // ProgressBar 보이기
        fun hideLoading() // ProgressBar 숨기기
        fun successSignIn(email:String, name:String) // 로그인 성공
        fun moveMainActivity()
    }

    // Signin Presenter가 구현해야할 interface
    interface Presenter : BasePresenter<View> {
        fun initGoogleSignIn(context: Context, key: String): Intent
        fun result(requestCode: Int, resulstCode: Int, data: Intent?)
        fun saveUser(context: Context, email:String, name:String)
        fun checkUser(context: Context)
    }

    // Signin Presenter Listener
    interface onSignInListener {
        fun onSuccess(email:String, name:String)
        fun onFailure()
        fun onEnd(context:Context, email:String, name:String)
        fun onGetUser(result:String?, context: Context, email:String, name:String)
    }
}