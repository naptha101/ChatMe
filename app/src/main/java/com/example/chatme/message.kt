package com.example.chatme

class message {
    var mes:String?=null
    var senderId:String?=null

    constructor(){}
    constructor(message:String,Id:String){
        this.mes=message
        this.senderId=Id

    }
}