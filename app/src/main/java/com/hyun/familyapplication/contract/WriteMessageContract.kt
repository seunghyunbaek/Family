package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface WriteMessageContract {
    interface View:BaseView {
        fun success()
        fun checkSuccess()
        fun checkFailure()
    }

    interface Presenter:BasePresenter<View> {
        fun sendMessage(context:Context, receiver:String, content:String)
        fun checkReceiver(context:Context, receiver: String)
    }

    interface Listener {
        fun onWriteSuccess(context: Context, result:String)
        fun onGetMessageCount(context: Context, result:String)
        fun onCreateMessageReceive(context: Context, receiver:String)
        fun onSuccess()
        fun checkSuccess()
        fun checkFailure()
    }
}