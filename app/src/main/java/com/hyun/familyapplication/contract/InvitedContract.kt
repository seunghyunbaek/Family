package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.model.Invite
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.Adapter.InvitedAdapter
import com.hyun.familyapplication.view.BaseView

class InvitedContract {
    interface View : BaseView {
        fun mainActivity()
    }

    interface Presenter: BasePresenter<View> {
        fun setAdapter(adapter:InvitedAdapter)
        fun getInvited(url:String, context: Context)
    }

    interface Listener {
        fun onSuccess(result:String)
        fun onPositive(context:Context, invite:Invite)
        fun onPutEnd(context: Context, result:String)
    }
}