package com.ianbl8.donationx.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
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

    override fun findAll(userid: String, donationsList: MutableLiveData<List<DonationModel>>) {
        val call = DonationClient.getApi().findall(userid)

        call.enqueue(object: Callback<List<DonationModel>> {
            override fun onResponse(
                call: Call<List<DonationModel>>,
                response: Response<List<DonationModel>>
            ) {
                donationsList.value = response.body() as ArrayList<DonationModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DonationModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() error: ${t.message}")
            }
        })
    }

    override fun findById(userid: String, donationid: String, donation: MutableLiveData<DonationModel>) {
        val call = DonationClient.getApi().get(userid, donationid)

        call.enqueue(object: Callback<DonationModel> {
            override fun onResponse(call: Call<DonationModel>, response: Response<DonationModel>) {
                donation.value = response.body() as DonationModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<DonationModel>, t: Throwable) {
                Timber.i("Retrofit findById() error: ${t.message}")
            }
        })

    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, donation: DonationModel) {
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

    override fun delete(userid: String, donationid: String) {
        val call = DonationClient.getApi().delete(userid, donationid)

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

    override fun update(userid: String, donationid: String, donation: DonationModel) {
        val call = DonationClient.getApi().put(userid, donationid, donation)

        call.enqueue(object: Callback<DonationWrapper> {
            override fun onResponse(
                call: Call<DonationWrapper>,
                response: Response<DonationWrapper>
            ) {
                val donationWrapper = response.body()
                if (donationWrapper != null) {
                    Timber.i("Retrofit update ${donationWrapper.message}")
                    Timber.i("Retrofit update ${donationWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DonationWrapper>, t: Throwable) {
                Timber.i("Retrofit update error: ${t.message}")
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