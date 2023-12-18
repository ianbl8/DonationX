package com.ianbl8.donationx.models

import androidx.lifecycle.MutableLiveData

interface DonationStore {
    fun findAll(donationsList: MutableLiveData<List<DonationModel>>)
    fun findAll(email: String, donationsList: MutableLiveData<List<DonationModel>>)
    fun findById(id: String): DonationModel?
    fun create(donation: DonationModel)
    fun delete(email: String, id: String)
}