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

    override fun checkRecord(context: Context) {
        MainModel.checkRecord(context, this)
    }

    override fun signOut(context: Context) {
        MainModel.signOut(context)
        view?.signInActivity()
    }

    // Listener
    override fun checkMessage(result: String) {
        val count = MainModel.getCount(result)
        view?.checkMessage(count)
    }

    override fun onCheckRecord(context:Context, result: String) {
        // 데이터 가져왔을 때
        MainModel.getRecord(context, result, this)
    }

    override fun onRecord(count: String, result: String) {
        val cnt = MainModel.getNewCount(count, result)
        view?.checkRecord(cnt)
    }

    override fun onError(msg: String) {
        view?.showError(msg)
    }
}