package com.hyun.familyapplication.model

import org.json.JSONArray

class Record {
    var id: Int = 0
    var email: String = ""
    var name : String = ""
    var date: String = ""
    var content:String = ""
    var room:Int = 0
    var record_images:JSONArray? = null
}