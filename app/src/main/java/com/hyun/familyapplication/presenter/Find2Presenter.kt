package com.hyun.familyapplication.presenter

import com.hyun.familyapplication.contract.Find2Contract

class Find2Presenter : Find2Contract.Presenter, Find2Contract.Listener {
    private var view:Find2Contract.View? = null

    override fun takeView(view: Find2Contract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun find(url:String) {

    }
}