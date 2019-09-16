package com.hyun.familyapplication.presenter

import android.content.Context
import android.content.Intent
import com.hyun.familyapplication.contract.SignInContract
import com.hyun.familyapplication.model.SignInModel

class SignInPresenter : SignInContract.Presenter, SignInContract.onSignInListener {

    private var signinView: SignInContract.View? = null
    private var signInModel: SignInModel

    constructor() {
        signInModel = SignInModel(this)
    }

    override fun takeView(view: SignInContract.View) {
        // view와 presenter를 연결한다
        signinView = view;
    }

    override fun dropView() {
        // view가 제거된 것을 presenter에게 알려준다
        signinView = null
    }

    override fun initGoogleSignIn(context: Context, key: String): Intent {
        signinView?.showLoading()
        return signInModel.initGoogleSignIn(context, key)
    }

    override fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        signInModel.result(requestCode, resultCode, data)
    }

    override fun onSuccess() {
        signinView?.hideLoading()
        signinView?.successSignIn()
    }

}