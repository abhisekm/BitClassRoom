package com.abhisekm.bitclassroom.ui.classroom.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.abhisekm.bitclassroom.database.getDatabase
import com.abhisekm.bitclassroom.repository.LessonRepository
import com.abhisekm.bitclassroom.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ClassroomViewModel(val lessonId: String, application: Application) :
    AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val lessonRepository = LessonRepository(database)

    val lesson = lessonRepository.lesson

    init {
        viewModelScope.launch {
            lessonRepository.getLesson(lessonId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Check remote user status
    private var _remoteUserConnected = MutableLiveData<Boolean>(false)
    val remoteUserConnected : LiveData<Boolean>
    get() = _remoteUserConnected

    fun remoteUserJoined(){
        _remoteUserConnected.value = true
    }

    fun remoteUserLeft(){
        _remoteUserConnected.value = false
    }

    // Check remote user video paused
    private var _remoteVideoPaused = MutableLiveData<Boolean>(false)
    val remoteVideoPaused: LiveData<Boolean>
    get() = _remoteVideoPaused

    fun remoteVideoPaused(value: Boolean){
        _remoteVideoPaused.value = value
        _eventRemoteVideoMuted.value = Event(value)
    }


    //state variable for toggle - Local audio
    private var _eventLocalAudioMuted = MutableLiveData<Event<Boolean>>(Event(false))
    val eventLocalAudioMuted: LiveData<Event<Boolean>>
        get() = _eventLocalAudioMuted

    //state variable for toggle - Local video
    private var _eventLocalVideoMuted = MutableLiveData<Event<Boolean>>(Event(false))
    val eventLocalVideoMuted: LiveData<Event<Boolean>>
        get() = _eventLocalVideoMuted

    //state variable for toggle - remote audio
    private var _eventRemoteAudioMuted = MutableLiveData<Event<Boolean>>(Event(false))
    val eventRemoteAudioMuted: LiveData<Event<Boolean>>
        get() = _eventRemoteAudioMuted

    //state variable for toggle - remote video
    private var _eventRemoteVideoMuted = MutableLiveData<Event<Boolean>>(Event(false))
    val eventRemoteVideoMuted: LiveData<Event<Boolean>>
        get() = _eventRemoteVideoMuted

    fun onToggleLocalAudio() {
        _eventLocalAudioMuted.value =
            Event(_eventLocalAudioMuted.value?.peekContent()?.not() ?: true)
    }

    fun onToggleLocalVideo() {
        _eventLocalVideoMuted.value =
            Event(_eventLocalVideoMuted.value?.peekContent()?.not() ?: true)
    }

    fun onToggleRemoteAudio() {
        _eventRemoteAudioMuted.value =
            Event(_eventRemoteAudioMuted.value?.peekContent()?.not() ?: true)
    }

    fun onToggleRemoteVideo() {
        _eventRemoteVideoMuted.value =
            Event(_eventRemoteVideoMuted.value?.peekContent()?.not() ?: true)
    }

    // Event to track camera switch
    private var _eventSwitchCamera = MutableLiveData<Event<Boolean>>()
    val eventSwitchCamera: LiveData<Event<Boolean>>
        get() = _eventSwitchCamera

    fun switchCamera() {
        _eventSwitchCamera.value = Event(true)
    }

    // Event to track end call
    private var _eventEndCall = MutableLiveData<Event<Boolean>>()
    val eventEndCall: LiveData<Event<Boolean>>
        get() = _eventEndCall

    fun endCall() {
        _eventEndCall.value = Event(true)
    }

    class Factory(val lessonId: String, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClassroomViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClassroomViewModel(lessonId, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}