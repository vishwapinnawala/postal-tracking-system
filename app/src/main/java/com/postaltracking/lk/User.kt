package com.postaltracking.lk

data class User(val name:String?=null,val nic:String?=null,val mobile:String?=null,val email:String?=null,val postal:String?=null,val pwd:String?=null){

}
data class trackingno(val pofficeid:String?=null,val trackno:String?=null,val time:String?=null){}
