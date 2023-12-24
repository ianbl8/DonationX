package com.ianbl8.donationx.ui.donate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.ianbl8.donationx.firebase.FirebaseDBManager
import com.ianbl8.donationx.firebase.FirebaseImageManager
import com.ianbl8.donationx.models.DonationModel
import java.lang.IllegalArgumentException

class DonateViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean> get() = status

    fun addDonation(firebaseUser: MutableLiveData<FirebaseUser>, donation: DonationModel) {
        donation.profilepic = FirebaseImageManager.imageUri.value.toString()
        status.value = try {
            FirebaseDBManager.create(firebaseUser, donation)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}