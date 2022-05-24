package kr.ac.kpu.oosoosoo.contents

import java.io.Serializable

data class ContentInfo (
    val id: String? = null,            //고유번호
    val _type : String?  = null,   //콘텐츠타입(TV or Movie)
    val title : String?  = null,        //제목
    val genre : String?  = null,        //장르(리스트)
    val production_countries : String?  = null, //제작 나라
    val vote_count : Int?  = null,              //평점수
    val vote_average : Float?  = null,          //평점
    var number_of_seasons : Int?  = null,       //시즌 수
    val number_of_episodes : Int?  = null,      //에피소드 수
    val release_date : String?  = null, //첫 방영일(개봉일) //Date형식으로 변경
    val adult : Int?  = null,       //청불여부
    val poster_path : String?  = null,  //포스터 주소
    val runtime : Int?  = null,         //런타임(분)
    val overview : String?  = null,     //설명
    val now_status : String?  = null,   //방영중인 상태(TV용)
    val flatrate : String?  = null,     //구독 서비스(리스트)
    val rent : String?  = null,         //대여 서비스(리스트)
    val buy : String?  = null,          //구매 서비스(리스트)
    val link : String? = null,          //TMDB 링크
) : Serializable