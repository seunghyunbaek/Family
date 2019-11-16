package com.hyun.familyapplication.presenter

import android.content.Context
import android.content.Intent
import com.hyun.familyapplication.contract.SignInContract
import com.hyun.familyapplication.model.SignInModel
import org.json.JSONObject
import java.lang.Exception
import java.lang.NullPointerException

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

    override fun saveUser(context: Context, email: String, name: String) {
        signInModel.getUserfromServer(context, email, name)
        /////////////
//        signInModel.saveUser(context, email, name)
    }

    override fun checkUser(context: Context) {
        val user = signInModel.getUser(context)
        if (user != null)
            signinView?.moveMainActivity()
    }

    // listener
    override fun onSuccess(email: String, name: String) {
        signinView?.successSignIn(email, name)
    }

    override fun onGetUser(result: String?, context: Context, email: String, name: String) {
        if (result != null) { // 로그인하려는 이메일의 유저가 서버에 있을 때
            val user = signInModel.getUser(context)
            val json = JSONObject(result)
            if (user == null) { // 유저가 SQLite에 저장되어 있지 않을 때
//                signInModel.saveUser(context, json.getString("email"), json.getString("name"))
                try {
                    val room = json.getInt("room")
                    signInModel.saveUserSQLite(context, json.getString("email"), json.getString("name"), room)
                    signinView?.moveMainActivity()
//                } catch (e:NullPointerException) {
                } catch (e:Exception) {
                    signInModel.saveUserSQLite(context, json.getString("email"), json.getString("name"))
                    signinView?.moveMainActivity()
                }
            } else { // 유저가 SQLite에 저장되어 있을 때
                signinView?.moveMainActivity()
            }
        } else { // 서버에 유저가 없을 때
            signInModel.saveUser(context, email, name)
        }
    }

    override fun onFailure() {
        signinView?.hideLoading()
    }

    override fun onEnd(context: Context, email: String, name: String) {
        println("---------------------------------------------------------------")
        println("통신 성공하고 리스너 실행하기")
        println("---------------------------------------------------------------")
        signInModel.saveUserSQLite(context, email, name)
        signinView?.hideLoading()
        signinView?.moveMainActivity()
    }
}