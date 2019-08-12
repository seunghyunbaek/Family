package com.hyun.myapplication.model

object UserList {
    fun getUserListData(): List<User> {
        return listOf(
            User("백승현1","a1@gmail.com" ),
            User("백승현2","a2@gmail.com"),
            User("백승현3","a3@gmail.com"),
            User("백승현4","a4@gmail.com")
        )
    }
}