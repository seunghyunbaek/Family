package com.hyun.myapplication.presenter

interface BasePresenter<T> {
    // 뷰가 생성 or bind 될 때 Presenter에 전달
    fun takeView(view:T)
    // view가 제거 or unbind될 때 Presenter에 전달
    fun dropView()
}