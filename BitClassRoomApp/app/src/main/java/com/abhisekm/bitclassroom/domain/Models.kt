package com.abhisekm.bitclassroom.domain

data class Lesson(
    var id: String,
    var createdAt: Long,
    var code: String,
    var heading: String,
    var startTime: Long,
    var endTime: Long,
)
