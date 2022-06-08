package kr.ac.kpu.oosoosoo.contents

import java.io.Serializable

data class People(
    val id: Int? = null,
    val name: String? = null,
    val birthday: String? = null,
    val department: String? = null,
    val popularity: Float? = null,
    val profile_path: String? = null,
) : Serializable

