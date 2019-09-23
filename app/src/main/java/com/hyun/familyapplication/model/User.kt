package com.hyun.familyapplication.model

class User {
    var email: String = ""
    var name: String = ""
    var hoching: String
    var gender: String = ""
    var phone: String = ""
    var anniversary: String = ""
    var roomId: Int? = null

    init {
        hoching = "나"
    }
}