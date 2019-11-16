package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.Adapter.FindAdapter
import com.hyun.familyapplication.view.BaseView

interface Find2Contract {
    interface View : BaseView {

    }

    interface Presenter: BasePresenter<View> {
        fun find(url:String)
    }

    interface Listener {
    }
}