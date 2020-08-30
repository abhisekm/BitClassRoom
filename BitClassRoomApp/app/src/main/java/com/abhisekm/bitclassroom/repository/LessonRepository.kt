package com.abhisekm.bitclassroom.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abhisekm.bitclassroom.database.LessonsDatabase
import com.abhisekm.bitclassroom.database.asDomainModel
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.network.Network
import com.abhisekm.bitclassroom.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LessonRepository(private val database: LessonsDatabase) {
    /**
     * A list of lessons that can be shown on the screen.
     */
    val lessons: LiveData<List<Lesson>> =
        Transformations.map(database.lessonDao.getLessons()) {
            it.asDomainModel()
        }

    private var _lesson = MutableLiveData<Lesson>()

    val lesson: LiveData<Lesson>
        get() = _lesson


    /**
     * Refresh the lessons stored in the offline cache.
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val lessons = Network.service.getLessons()
            database.lessonDao.insertAll(*lessons.asDatabaseModel())
        }
    }


    suspend fun getLesson(id: String) {
        withContext(Dispatchers.IO) {
            database.lessonDao.getLesson(id).asDomainModel()?.let {
                withContext(Dispatchers.Main) {
                    _lesson.value = it
                }
            }
        }
    }


}