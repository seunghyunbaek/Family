package com.hyun.familyapplication.contract

import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface RecordContract {
    // Record View가 구현해야할 interface
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showRecord() // 기록 보여주기
    }

    // RecordPresent가 구현해야할 interface
    interface Presenter : BasePresenter<View> {
        // 기록물 가져오기
        fun getRecord()
        // 새로운 Record 추가
        fun updateRecord()
    }

    interface onRecordListener {
        fun onSuccess(result:String)
        fun onFailed()
        fun onEnd(result:String)
    }
}