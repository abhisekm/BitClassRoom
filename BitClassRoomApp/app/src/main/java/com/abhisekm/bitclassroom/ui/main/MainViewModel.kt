package com.abhisekm.bitclassroom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abhisekm.bitclassroom.database.Classroom

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _navigateToClassroom = MutableLiveData<Classroom>()

    val navigateToClassroom: LiveData<Classroom>
    get() = _navigateToClassroom

    fun doneNavigating(){
        _navigateToClassroom.value = null
    }

    fun joinClassroom(){
        _navigateToClassroom.value = Classroom()
    }
}