package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface MainContract {

    interface View : BaseView {
        fun checkMessage(count:Int)
    }

    interface Presenter : BasePresenter<View> {
        fun checkMessage(context: Context)
    }

    interface Listener {
        fun checkMessage(result:String)
    }
}