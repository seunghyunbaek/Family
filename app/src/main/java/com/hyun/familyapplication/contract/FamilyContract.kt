package com.hyun.familyapplication.contract

import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface FamilyContract {
    // Family View 가 구현해야할 인터페이스
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showList()
    }

    // Family Presenter가 구현해야할 인터페이스
    interface Presenter: BasePresenter<View> {
        fun getFamily()
        fun findFamily()

    }

    interface FamilyListener {
        fun onSuccess()
        fun onEnd()
        fun onFailure()
    }
}