package com.abhisekm.bitclassroom.database

data class DatabaseLesson(
    var id: String,
    var createdAt: Long,
    var code: String,
    var heading: String,
    var startTime: Long,
    var endTime: Long,
)