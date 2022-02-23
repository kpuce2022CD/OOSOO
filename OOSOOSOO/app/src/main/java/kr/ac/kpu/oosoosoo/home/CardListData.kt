package kr.ac.kpu.oosoosoo.home

import kr.ac.kpu.oosoosoo.contents.ContentInfo

data class CardListData(
    var cardListTitle: String?,
    var cardItemList: List<ContentInfo>?
)
