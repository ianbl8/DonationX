package com.ianbl8.donationx.models

import androidx.lifecycle.MutableLiveData

interface DonationStore {
    fun findAll(donationsList: MutableLiveData<List<DonationModel>>)
    fun findById(id: String): DonationModel?
    fun create(donation: DonationModel)
    fun delete(id: String)
}