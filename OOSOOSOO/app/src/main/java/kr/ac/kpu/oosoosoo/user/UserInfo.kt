package kr.ac.kpu.oosoosoo.user

import java.io.Serializable

data class UserInfo (
    val email: String?,             //이메일
    val passwd : String?,           //비밀번호
    val name : String?,             //이름
    val phone_number : String?,     //전화번호
    val nickname : String?,         //닉네임
    val gender : Int?,              //성별
    val birthday : String?,         //생년월일
    var job : String?,              //직업
    val overview : String?,         //소개
    val coin : String?,             //코인
) : Serializable