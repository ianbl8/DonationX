package com.ianbl8.donationx.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.donationx.models.DonationManager
import com.ianbl8.donationx.models.DonationModel

class DonationDetailViewModel : ViewModel() {
    private val donation = MutableLiveData<DonationModel>()

    val observableDonation: LiveData<DonationModel> get() = donation

    fun getDonation(id: String) {
        donation.value = DonationManager.findById(id)
    }
}