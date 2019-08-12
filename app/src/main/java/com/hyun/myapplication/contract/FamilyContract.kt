package com.hyun.myapplication.contract

import com.hyun.myapplication.presenter.BasePresenter
import com.hyun.myapplication.view.BaseView

interface FamilyContract {
    // Family View 가 구현해야할 인터페이스
    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showList()
    }

    // Family Presenter가 구현해야할 인터페이스
    interface Presenter: BasePresenter<View> {
        // Family Data 가져오기
        fun getFamily()
        // 새로운 Family 추가하기
        fun updateFamily()
    }
}