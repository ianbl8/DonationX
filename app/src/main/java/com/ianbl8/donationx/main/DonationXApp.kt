package com.ianbl8.donationx.main

import android.app.Application
import com.ianbl8.donationx.models.DonationMemStore
import com.ianbl8.donationx.models.DonationStore
import timber.log.Timber

class DonationXApp: Application() {

    lateinit var donationsStore: DonationStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        donationsStore = DonationMemStore()
        Timber.i("Starting DonationX application")
    }
}