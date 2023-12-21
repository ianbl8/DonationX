package com.ianbl8.donationx.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.donationx.firebase.FirebaseDBManager
import com.ianbl8.donationx.models.DonationModel
import timber.log.Timber

class DonationDetailViewModel : ViewModel() {
    private val donation = MutableLiveData<DonationModel>()

    var observableDonation: LiveData<DonationModel>
        get() = donation
        set (value) {donation.value = value.value}

    fun getDonation(userid: String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, donation)
            Timber.i("Detail getDonation() = ${donation.value.toString()}")
        } catch (e: Exception) {
            Timber.i("Detail getDonation() error: ${e.message}")
        }
    }

    fun updateDonation(userid: String, id: String, donation: DonationModel) {
        try {
            FirebaseDBManager.update(userid, id, donation)
            Timber.i("Detail updateDonation() = ${donation}")
        } catch (e: Exception) {
            Timber.i("Detail updateDonation() error: ${e.message}")
        }
    }
}