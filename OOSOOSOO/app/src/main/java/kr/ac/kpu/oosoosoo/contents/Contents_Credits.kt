package kr.ac.kpu.oosoosoo.contents

import java.io.Serializable

data class Contents_Credits(
    val id: String? = null,
    val c_id: String? = null,
    val p_id: Int? = null,
    val job: String? = null,
    val role: String? = null,
) : Serializable

