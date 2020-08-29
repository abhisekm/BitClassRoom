package com.abhisekm.bitclassroom.database

data class Classroom(
    var id: String = "100",
    var createdAt: Long = System.currentTimeMillis(),
    var code: String = "code 0",
    var heading: String = "heading 0",
    var startTime: Long = System.currentTimeMillis(),
    var endTime: Long = System.currentTimeMillis(),
)