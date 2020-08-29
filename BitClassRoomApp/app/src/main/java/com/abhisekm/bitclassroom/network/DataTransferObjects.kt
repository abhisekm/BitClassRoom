package com.abhisekm.bitclassroom.network

import com.abhisekm.bitclassroom.database.DatabaseLesson
import com.abhisekm.bitclassroom.domain.Lesson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkLesson (
    val id: String,
    val createdAt: Long,
    val code: String,
    val heading: String,
    @Json(name = "start_time") val startTime: Long,
    @Json(name = "end_time") val endTime: Long,
)

/**
 * Convert network to domain objects. Encapsulate domain from network
 */
fun NetworkLesson.asDomainModel() : Lesson {
    return Lesson(
        id,
        createdAt,
        code,
        heading,
        startTime,
        endTime
    )
}

/**
 * Convert network to database objects. Encapsulate database from network
 */
fun List<NetworkLesson>.asDatabaseModel() : Array<DatabaseLesson> {
    return map {
        DatabaseLesson(
            id = it.id,
            createdAt = it.createdAt,
            code = it.code,
            heading = it.heading,
            startTime = it.startTime,
            endTime = it.endTime
        )
    }.toTypedArray()
}