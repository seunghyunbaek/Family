package com.hyun.familyapplication.model

class Record {
    var id : Int = 0
    var name : String = ""
    var date: String = ""
    var content:String = ""

    constructor(){}
    constructor(name: String, date: String, content: String) {
        this.name = name
        this.date = date
        this.content = content
    }
}