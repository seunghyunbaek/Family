package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface WriteMessageContract {
    interface View:BaseView {
        fun success()
    }

    interface Presenter:BasePresenter<View> {
        fun sendMessage(context:Context, receiver:String, content:String)
    }

    interface Listener {
        fun onSuccess()
    }
}