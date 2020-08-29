package com.abhisekm.bitclassroom.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LessonDao {
    @Query("select * from lessons_table")
    fun getLessons(): LiveData<List<DatabaseLesson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg lessons: DatabaseLesson)
}


@Database(entities = [DatabaseLesson::class], version = 1, exportSchema = false)
abstract class LessonsDatabase : RoomDatabase() {
    abstract val lessonDao: LessonDao
}

private lateinit var INSTANCE: LessonsDatabase

fun getDatabase(context: Context): LessonsDatabase {
    synchronized(LessonsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                LessonsDatabase::class.java,
                "lessons").build()
        }
    }
    return INSTANCE
}