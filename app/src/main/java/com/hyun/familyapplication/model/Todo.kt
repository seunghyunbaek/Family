package com.hyun.familyapplication.model

class Todo {
    var id: Int = 0
    var title: String = ""
    var date: String = ""
//    constructor(title: String, date: String) {
//        this.title = title
//        this.date = date
//    }
    constructor(){}
    constructor(title:String, date:String) {
        this.title = title
        this.date = date
    }
}