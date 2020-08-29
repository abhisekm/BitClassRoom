package com.abhisekm.bitclassroom.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhisekm.bitclassroom.database.getDatabase
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.repository.LessonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, DONE, ERROR }

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val lessonRepository = LessonRepository(database)

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    val lessons = lessonRepository.lessons

    init {
        viewModelScope.launch {
            lessonRepository.refreshVideos()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToClassroom = MutableLiveData<Lesson>()

    val navigateToDatabaseLesson: LiveData<Lesson>
        get() = _navigateToClassroom

    fun doneNavigating() {
        _navigateToClassroom.value = null
    }

    fun joinClassroom(lesson: Lesson) {
        val time = System.currentTimeMillis()
        _navigateToClassroom.value =
            lesson ?: Lesson("test", time, "code test", "heading test", time, time)
    }
}