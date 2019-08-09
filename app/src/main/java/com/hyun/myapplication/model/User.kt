package com.hyun.myapplication.model

class User(val name: String, val email: String) {

    lateinit var appellation:String
    lateinit var phone:String
    lateinit var memo:String

    fun checkUserValidity(email: String):Boolean {
        return this.email == email
    }

}