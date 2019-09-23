package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface MyHomeCheckContract {
    interface View:BaseView {
        fun showLoading()
        fun hideLoading()
        fun createdRoom()
    }

    interface Presenter : BasePresenter<View> {
        fun createRoom(context:Context)
        fun checkRoom(context:Context)
    }
}