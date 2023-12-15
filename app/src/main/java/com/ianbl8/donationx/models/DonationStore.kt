package com.ianbl8.donationx.models

interface DonationStore {
    fun findAll(): List<DonationModel>
    fun findById(id: Long): DonationModel?
    fun create(donation: DonationModel)
}