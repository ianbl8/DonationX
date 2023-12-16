package com.ianbl8.donationx.ui.donate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianbl8.donationx.models.DonationManager
import com.ianbl8.donationx.models.DonationModel
import java.lang.IllegalArgumentException

class DonateViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean> get() = status

    fun addDonation(donation: DonationModel) {
        status.value = try {
            DonationManager.create(donation)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}