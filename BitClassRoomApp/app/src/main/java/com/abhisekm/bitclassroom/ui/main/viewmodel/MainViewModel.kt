package com.abhisekm.bitclassroom.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.bitclassroom.domain.Lesson
import com.abhisekm.bitclassroom.network.Network
import com.abhisekm.bitclassroom.network.asDomainModel
import kotlinx.coroutines.launch
import java.lang.Exception

enum class ApiStatus { LOADING, DONE, ERROR }

class MainViewModel() : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    private val _lessons = MutableLiveData<List<Lesson>>()

    val lessons: LiveData<List<Lesson>>
        get() = _lessons

    init {
        getLessons()
    }

    private fun getLessons() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val networkLesson = Network.service.getLessons().await()
                _lessons.value = networkLesson.map { it.asDomainModel() }
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
                _lessons.value = ArrayList()
            }
        }
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