package kr.ac.kpu.oosoosoo.contents
import java.io.Serializable

data class ReviewInfo (
    val id: Int,                //고유 번호
    val c_id: String,           //콘텐츠 아이디
    val u_eamil: String,        //유저 이메일
    val _like: Int,             //좋아요 받은 수
    val rating: Float,          //평점
    val review: String,         //리뷰
    val datetime: String,       //리뷰 남긴 시간
) : Serializable

