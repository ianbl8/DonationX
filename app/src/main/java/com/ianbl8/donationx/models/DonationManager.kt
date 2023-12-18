package com.ianbl8.donationx.models

import androidx.lifecycle.MutableLiveData
import com.ianbl8.donationx.api.DonationClient
import com.ianbl8.donationx.api.DonationWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/*
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
 */

object DonationManager: DonationStore {
    private val donations = ArrayList<DonationModel>()

    override fun findAll(donationsList: MutableLiveData<List<DonationModel>>) {
        val call = DonationClient.getApi().findall()

        call.enqueue(object: Callback<List<DonationModel>> {
            override fun onResponse(
                call: Call<List<DonationModel>>,
                response: Response<List<DonationModel>>
            ) {
                donationsList.value = response.body() as ArrayList<DonationModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DonationModel>>, t: Throwable) {
                Timber.i("Retrofit error: ${t.message}")
            }
        })
    }

    override fun findAll(email: String, donationsList: MutableLiveData<List<DonationModel>>) {
        val call = DonationClient.getApi().findall(email)

        call.enqueue(object: Callback<List<DonationModel>> {
            override fun onResponse(
                call: Call<List<DonationModel>>,
                response: Response<List<DonationModel>>
            ) {
                donationsList.value = response.body() as ArrayList<DonationModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DonationModel>>, t: Throwable) {
                Timber.i("Retrofit error: ${t.message}")
            }
        })
    }

    override fun findById(id: String): DonationModel? {
        val foundDonation: DonationModel? = donations.find { it._id == id }
        return foundDonation
    }

    override fun create(donation: DonationModel) {
        val call = DonationClient.getApi().post(donation.email, donation)

        call.enqueue(object: Callback<DonationWrapper> {
            override fun onResponse(
                call: Call<DonationWrapper>,
                response: Response<DonationWrapper>
            ) {
                val donationWrapper = response.body()
                if (donationWrapper != null) {
                    Timber.i("Retrofit ${donationWrapper.message}")
                    Timber.i("Retrofit ${donationWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DonationWrapper>, t: Throwable) {
                Timber.i("Retrofit error: ${t.message}")
            }
        })
    }

    override fun delete(email: String, id: String) {
        val call = DonationClient.getApi().delete(email, id)

        call.enqueue(object: Callback<DonationWrapper> {
            override fun onResponse(
                call: Call<DonationWrapper>,
                response: Response<DonationWrapper>
            ) {
                val donationWrapper = response.body()
                if (donationWrapper != null) {
                    Timber.i("Retrofit delete ${donationWrapper.message}")
                    Timber.i("Retrofit delete ${donationWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DonationWrapper>, t: Throwable) {
                Timber.i("Retrofit delete error: ${t.message}")
            }
        })
    }

/*
    fun logAll() {
        Timber.v("** Donations List **")
        donations.forEach {
            Timber.v("Donate ${it}")
        }
    }
 */
}