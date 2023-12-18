package com.ianbl8.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.ianbl8.donationx.models.DonationManager
import com.ianbl8.donationx.models.DonationModel
import timber.log.Timber

class ReportViewModel : ViewModel() {
    private val donationsList = MutableLiveData<List<DonationModel>>()
    val observableDonationsList: LiveData<List<DonationModel>>
        get() = donationsList

    val liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            DonationManager.findAll(liveFirebaseUser.value?.email!!, donationsList)
            Timber.i("Retrofit success: ${donationsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit error: ${e.message}")
        }
    }

    fun delete(email: String, id: String) {
        try {
            DonationManager.delete(email, id)
            Timber.i("Retrofit delete success for ${id}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit delete error: ${e.message}")
        }
    }

}