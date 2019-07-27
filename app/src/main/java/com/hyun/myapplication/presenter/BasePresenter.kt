package com.hyun.myapplication.presenter

interface BasePresenter<T> {
    fun takeView(view:T)
    fun dropView()
}