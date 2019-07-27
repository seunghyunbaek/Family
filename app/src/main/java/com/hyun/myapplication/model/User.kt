package com.hyun.myapplication.model

//data class User(
//    val email: String,
//    val passwd: String
//)

class User(val email: String,val passwd: String) {

    fun checkUserValidity(email: String, passwd: String):Boolean {
        return this.email == email && this.passwd == passwd
    }

}