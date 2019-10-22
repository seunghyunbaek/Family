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
    }

    // Family Presenter가 구현해야할 인터페이스
    interface Presenter: BasePresenter<View> {
        fun getFamily(context: Context)
        fun findFamily()
        fun takeAdapter(adapter:FamilyAdapter)
    }

    interface Listener {
        fun onSuccess(result:String, email:String)
        fun onDeleteRoom(context: Context)
        fun onDelSuccess(context:Context)
        fun onExit()
    }
}