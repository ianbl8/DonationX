package com.ianbl8.donationx.ui.donate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DonateViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Fragment"
    }
    val text: LiveData<String> = _text
}