package com.hyun.myapplication.contract

import android.view.View
import com.hyun.myapplication.presenter.BasePresenter
import com.hyun.myapplication.view.BaseView

interface SignInContract {
    // Signin View가 구현해야할 interface
    interface View: BaseView {
        // 데이터를 받아서 정제하는 동안 보일 ProgressBar 관리
        fun showLoading() // ProgressBar 보이기
        fun hideLoading() // ProgressBar 숨기기
    }

    // Signin Presenter가 구현해야할 interface
    interface Presenter : BasePresenter<View> {
        // 모델로부터 데이터를 받아오기(정제하기)위한 함수
        fun doLogin(email:String, passwd: String): Boolean // 로그인하기
    }
}