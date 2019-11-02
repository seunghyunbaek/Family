package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.MessageContract
import com.hyun.familyapplication.model.MessageModel

class MessagePresenter : MessageContract.Presenter, MessageContract.Listener {
    var view: MessageContract.View? = null

    override fun takeView(view: MessageContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun getData(context: Context) {
        MessageModel.getData(context, this)
    }

    override fun onSuccess(result: String) {
        val list = MessageModel.parseArray(result)
        view?.setData(list)
    }
}