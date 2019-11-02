package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.MainContract
import com.hyun.familyapplication.model.MainModel

class MainPresenter : MainContract.Presenter, MainContract.Listener {

    private var view: MainContract.View? = null

    override fun takeView(view: MainContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun checkMessage(context: Context) {
        MainModel.checkMessage(context, this)
    }

    // Listener
    override fun checkMessage(result: String) {
        val count = MainModel.getCount(result)
        view?.checkMessage(count)
    }


}