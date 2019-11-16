package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.Adapter.FamilyAdapter
import com.hyun.familyapplication.view.BaseView

interface FamilyContract {
    // Family View 가 구현해야할 인터페이스
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun transferActivity()
        fun mainActivity()
        fun exitRoom(bool:Boolean)
    }

    // Family Presenter가 구현해야할 인터페이스
    interface Presenter: BasePresenter<View> {
        fun getFamily(context: Context)
        fun findFamily()
        fun takeAdapter(adapter:FamilyAdapter)
        fun getHost(context: Context)
    }

    interface Listener {
        fun onSuccess(result:String, email:String)
        fun onDeleteRoom(context: Context)
        fun onDelSuccess(context:Context)
        fun onExit(context: Context)
        fun onExitRoom(context:Context, result:String, bool:Boolean)
        fun getHostResult(context:Context, result:String)
    }
}