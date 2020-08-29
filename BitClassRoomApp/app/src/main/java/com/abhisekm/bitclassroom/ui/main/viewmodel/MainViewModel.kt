package com.abhisekm.bitclassroom.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.bitclassroom.database.getDatabase
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.repository.LessonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

enum class ApiStatus { LOADING, DONE, ERROR }

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
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(lessons.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
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
        _navigateToClassroom.value = lesson
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