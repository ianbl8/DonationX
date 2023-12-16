package com.ianbl8.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.donationx.models.DonationManager
import com.ianbl8.donationx.models.DonationModel

class ReportViewModel : ViewModel() {
    private val donationsList = MutableLiveData<List<DonationModel>>()
    val observableDonationsList: LiveData<List<DonationModel>>
        get() = donationsList

    init {
        load()
    }

    fun load() {
        donationsList.value = DonationManager.findAll()
    }
}