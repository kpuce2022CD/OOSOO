package kr.ac.kpu.oosoosoo.login

import org.json.JSONObject

data class NaverProfileInfo(
    var resultcode: String,
    var message: String,
    var response: JSONObject
)

data class response(
    var id : String,
    var nickname : String,
    var gender : String,
    var email : String,
    var mobile : String,
    var mobile_e164 : String,
    var name : String,
    var birthyear :String,
    var birthday : String
)
