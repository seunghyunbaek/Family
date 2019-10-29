package com.hyun.familyapplication.contract

import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

class MessageContract {
    interface View:BaseView {

    }

    interface Presenter:BasePresenter<View> {

    }

    interface Listener {

    }
}