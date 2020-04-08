package com.example.jetpackexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class DataItems {
    @PrimaryKey
    var name:String
    var address:String
    var email:String
    var mobile:String


    constructor(name: String, address: String, email: String, mobile: String) {
        this.name = name
        this.address = address
        this.email = email
        this.mobile = mobile
    }
}