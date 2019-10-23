package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.model.User
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.Adapter.TransferAdapter
import com.hyun.familyapplication.view.BaseView

interface TransferContract {
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun mainActivity()
    }

    interface Presenter: BasePresenter<View> {
        fun getFamily(context: Context)
        fun takeAdapter(adapter: TransferAdapter)
    }

    interface Listener {
        fun onSuccess(result:String, email:String)
        fun onSelected(context: Context, user: User)
        fun onUserChange(context: Context)
        fun onUserChangeSQLite(context:Context, result:String)
        fun onSuccess()
    }
}