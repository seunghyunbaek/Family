package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface MainContract {

    interface View : BaseView {
        fun checkMessage(count:Int)
        fun checkRecord(count:Int)
        fun signInActivity()
    }

    interface Presenter : BasePresenter<View> {
        fun checkMessage(context: Context)
        fun checkRecord(context:Context)
        fun signOut(context:Context)
    }

    interface Listener {
        fun checkMessage(result:String)
        fun onCheckRecord(context:Context, result:String)
        fun onRecord(count:String, result:String)
        fun onError(msg:String)
    }
}