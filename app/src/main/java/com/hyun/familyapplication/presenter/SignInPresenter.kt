package com.hyun.familyapplication.presenter

import android.os.Handler
import com.hyun.familyapplication.contract.SignInContract
import com.hyun.familyapplication.model.User
import com.hyun.familyapplication.model.UserList.getUserListData

class SignInPresenter : SignInContract.Presenter {

    private var signinView: SignInContract.View? = null

    override fun takeView(view: SignInContract.View) {
        // view와 presenter를 연결한다
        signinView = view;
    }

    override fun dropView() {
        // view가 제거된 것을 presenter에게 알려준다
        signinView = null
    }

//    override fun doLogin(email: String, passwd: String):Boolean {
////        signinView?.showLoading()
//
//        var isSuccess:Boolean = false
//        // 로그인 성공 실패 체크하기
//        val userList:List<User> = getUserListData()
//
//        // 입력한 데이터의 유저가 있는지 확인하기
//        for(item in userList) {
//            if(item.checkUserValidity(email, passwd)) {
//                isSuccess = true
//                break
//            }
//        }
//
////        Handler().postDelayed({
////            signinView?.hideLoading()
////        }, 500)
//
//        return isSuccess
//    }

    override fun doLogin(email: String) {
        signinView?.showLoading()

        Handler().postDelayed({
            var isSuccess: Boolean = false
            // 로그인 성공 실패 체크하기
            val userList: List<User> = getUserListData()

            // 입력한 데이터의 유저가 있는지 확인하기
            for (item in userList) {
//                if (item.checkUserValidity(email)) {
//                    isSuccess = true
//                    break
//                }
            }

            signinView?.hideLoading()
            if(isSuccess)
                signinView?.successSignIn()
        }, 500)
    }
}