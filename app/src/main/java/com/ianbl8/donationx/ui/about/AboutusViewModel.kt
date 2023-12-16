package com.ianbl8.donationx.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutusViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Fragment"
    }
    val text: LiveData<String> = _text
}