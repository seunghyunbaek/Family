package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.WriteMessageContract
import com.hyun.familyapplication.model.WriteMessageModel

class WriteMessagePresenter : WriteMessageContract.Presenter, WriteMessageContract.Listener {
    private var view:WriteMessageContract.View? = null
    override fun sendMessage(context: Context, receiver: String, content: String) {
        WriteMessageModel.sendMessage(context, receiver, content, this)
    }

    override fun takeView(view: WriteMessageContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }


    // Listener
    override fun onWriteSuccess(context: Context, result:String) {
//        view?.success()
        WriteMessageModel.getMessageCount(context, result, this)
    }

    override fun onCreateMessageReceive(context: Context, receiver: String) {
        WriteMessageModel.createMessageReceive(context, this, receiver)
    }

    override fun onGetMessageCount(context: Context, result: String) {
        WriteMessageModel.addMessageCount(context, result, this)
    }

    override fun onSuccess() {
        view?.success()
    }
}