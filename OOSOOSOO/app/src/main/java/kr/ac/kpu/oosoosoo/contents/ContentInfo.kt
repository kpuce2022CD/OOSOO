package kr.ac.kpu.oosoosoo.contents

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContentInfo (
    val id: String?,            //고유번호
    val field_type : String?,   //콘텐츠타입(TV or Movie)
    val title : String?,        //제목
    val genre : String?,        //장르(리스트)
    val production_countries : String?, //제작 나라
    val vote_count : Int?,              //평점수
    val vote_average : Float?,          //평점
    var number_of_seasons : Int?,       //시즌 수
    val number_of_episodes : Int?,      //에피소드 수
    val release_date : String?, //첫 방영일(개봉일) //Date형식으로 변경
    val adult : Int?,       //청불여부
    val poster_path : String?,  //포스터 주소
    val runtime : Int?,         //런타임(분)
    val overview : String?,     //설명
    val now_status : String?,   //방영중인 상태(TV용)
    val flatrate : String?,     //구독 서비스(리스트)
    val rent : String?,         //대여 서비스(리스트)
    val buy : String?           //구매 서비스(리스트)
) : Parcelable