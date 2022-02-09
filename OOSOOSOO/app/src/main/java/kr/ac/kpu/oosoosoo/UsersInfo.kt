package kr.ac.kpu.oosoosoo

data class UsersInfo (
    val id: Int?,                   //고유번호
    val email: String?,             //이메일
    val passwd: String?,            //비밀번호
    val name: String?,              //이름
    val phone_number: String?,      //전화번호
    val nickname: String?,          //별명
    val gender: Boolean?,           //성별
    val age: Int?,                  //나이
    val job: String?,               //직업
    val overview: String?,          //간단한 소개
    val coin: Int?                  //코인
)