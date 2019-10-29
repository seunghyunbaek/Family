package com.hyun.familyapplication.presenter

import com.hyun.familyapplication.contract.MessageContract

class MessagePresenter : MessageContract.Presenter {
    var view:MessageContract.View? = null

    override fun takeView(view: MessageContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}