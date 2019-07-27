package com.hyun.myapplication.model

object UserList {
    fun getUserListData(): List<User> {
        return listOf(
            User("a1@gmail.com", "123"),
            User("a2@gmail.com", "123"),
            User("a3@gmail.com", "123"),
            User("a4@gmail.com", "123")
        )
    }
}