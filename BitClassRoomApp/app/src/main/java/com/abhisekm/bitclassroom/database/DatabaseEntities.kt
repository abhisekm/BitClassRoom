package com.abhisekm.bitclassroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhisekm.bitclassroom.domain.Lesson

@Entity(tableName = "lessons_table")
data class DatabaseLesson(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,

    var code: String,

    var heading: String,

    @ColumnInfo(name = "start_time")
    var startTime: Long,

    @ColumnInfo(name = "end_time")
    var endTime: Long,
)

fun List<DatabaseLesson>.asDomainModel(): List<Lesson> {
    return map {
        Lesson(
            id = it.id,
            createdAt = it.createdAt,
            code = it.code,
            heading = it.heading,
            startTime = it.startTime,
            endTime = it.endTime
        )
    }
}