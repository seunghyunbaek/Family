package com.hyun.myapplication.contract

import com.hyun.myapplication.presenter.BasePresenter
import com.hyun.myapplication.view.BaseView

interface RecordContract {
    // Record View가 구현해야할 interface
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showRecord() // 기록 보여주기
        fun updateRecord()
    }

    // RecordPresent가 구현해야할 interface
    interface Presenter : BasePresenter<View> {
        // 모델로부터 데이터를 받아와 정제할 함수
        fun getRecord()
    }
}