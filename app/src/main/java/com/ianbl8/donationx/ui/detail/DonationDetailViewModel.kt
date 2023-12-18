package com.ianbl8.donationx.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.donationx.models.DonationManager
import com.ianbl8.donationx.models.DonationModel
import timber.log.Timber

class DonationDetailViewModel : ViewModel() {
    private val donation = MutableLiveData<DonationModel>()

    val observableDonation: LiveData<DonationModel> get() = donation

    fun getDonation(email: String, id: String) {
        try {
            DonationManager.findById(email, id, donation)
            Timber.i("Detail getDonation() = ${donation.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getDonation() error: ${e.message}")
        }
    }

    fun updateDonation(email: String, id: String, donation: DonationModel) {
        try {
            DonationManager.update(email, id, donation)
            Timber.i("Detail updateDonation() = ${donation}")
        }
        catch (e: Exception) {
            Timber.i("Detail updateDonation() error: ${e.message}")
        }
    }
}