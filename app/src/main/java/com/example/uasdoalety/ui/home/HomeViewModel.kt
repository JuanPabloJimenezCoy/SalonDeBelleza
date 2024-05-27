package com.example.uasdoalety.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Para agendar tu cita de Uñas dale al boton ' + '"
    }
    val text: LiveData<String> = _text
}