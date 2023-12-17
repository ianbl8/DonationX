package com.ianbl8.donationx.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DonationModel(
    var id: Long = 0,
    @SerializedName("paymenttype")
    val paymentmethod: String = "N/A",
    val message: String = "n/a",
    val amount: Int = 0,
) : Parcelable