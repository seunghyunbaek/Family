package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.model.Message
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

class MessageContract {
    interface View:BaseView {
        fun setData(list:ArrayList<Message>)
    }

    interface Presenter:BasePresenter<View> {
        fun getData(context: Context)
        fun readMessage(context:Context)
    }

    interface Listener {
        fun onSuccess(result:String)
    }
}