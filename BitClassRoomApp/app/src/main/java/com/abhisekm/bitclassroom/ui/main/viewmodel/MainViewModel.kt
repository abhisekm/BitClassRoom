package com.abhisekm.bitclassroom.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.bitclassroom.database.getDatabase
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.repository.LessonRepository
import com.abhisekm.bitclassroom.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val lessonRepository = LessonRepository(database)

    val lessons = lessonRepository.lessons

    init {
        viewModelScope.launch {
            try {
                lessonRepository.refreshVideos()
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if (lessons.value.isNullOrEmpty())
                    _eventNetworkError.value = Event(true)
            }
        }
    }

    private var _eventNetworkError = MutableLiveData<Event<Boolean>>()

    val eventNetworkError: LiveData<Event<Boolean>>
        get() = _eventNetworkError


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToClassroom = MutableLiveData<Event<Lesson>>()

    val navigateToDatabaseLesson: LiveData<Event<Lesson>>
        get() = _navigateToClassroom

    fun joinClassroom(lesson: Lesson) {
        _navigateToClassroom.value = Event(lesson)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}