package com.ianbl8.donationx.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.ianbl8.donationx.firebase.FirebaseDBManager
import com.ianbl8.donationx.models.DonationModel
import timber.log.Timber

class ReportViewModel : ViewModel() {
    private val donationsList = MutableLiveData<List<DonationModel>>()
    val observableDonationsList: LiveData<List<DonationModel>>
        get() = donationsList

    val liveFirebaseUser = MutableLiveData<FirebaseUser>()
    var readOnly = MutableLiveData(false)

    init {
        load()
    }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, donationsList)
            Timber.i("Report success: ${donationsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report error: ${e.message}")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(donationsList)
            Timber.i("Report success: ${donationsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report error: ${e.message}")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid, id)
            Timber.i("Retrofit delete success for ${id}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit delete error: ${e.message}")
        }
    }

}