package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.Adapter.FindAdapter
import com.hyun.familyapplication.view.BaseView

interface FindContract {
    interface View : BaseView {

    }

    interface Presenter:BasePresenter<View> {
        fun setAdapter(adapte:FindAdapter)
        fun findUser(context: Context, email:String)
        fun inviteUser(url:String, room:Int, inviter:String, guest:String)
    }

    interface Listener {
        fun onSuccess(result:String)
    }
}