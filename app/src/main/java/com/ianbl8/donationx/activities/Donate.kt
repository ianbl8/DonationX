package com.ianbl8.donationx.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ianbl8.donationx.R
import com.ianbl8.donationx.databinding.ActivityDonateBinding
import com.ianbl8.donationx.main.DonationXApp
import com.ianbl8.donationx.models.DonationModel
import timber.log.Timber

lateinit var app: DonationXApp

class Donate : AppCompatActivity() {
    private lateinit var donateLayout: ActivityDonateBinding
    var totalDonated = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = this.application as DonationXApp
        donateLayout = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(donateLayout.root)

        donateLayout.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
        donateLayout.progressBar.progress = totalDonated
        donateLayout.progressBar.max = 10000
        donateLayout.amountPicker.minValue = 1
        donateLayout.amountPicker.maxValue = 1000

        donateLayout.amountPicker.setOnValueChangedListener { _, _, newVal ->
            donateLayout.paymentAmount.setText("$newVal")
        }

        donateLayout.donateButton.setOnClickListener {
            val amount = if (donateLayout.paymentAmount.text.isNotEmpty()) {
                donateLayout.paymentAmount.text.toString()
                    .toInt()
            } else {
                donateLayout.amountPicker.value
            }
            if (totalDonated >= donateLayout.progressBar.max) {
                Toast.makeText(applicationContext, "Donate amount exceeded!", Toast.LENGTH_LONG)
                    .show()
            } else {
                val paymentmethod =
                    if (donateLayout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                Timber.i("Total donated so far: \$$totalDonated")
                app.donationsStore.create(
                    DonationModel(
                        paymentmethod = paymentmethod,
                        amount = amount
                    )
                )
                donateLayout.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
                donateLayout.progressBar.progress = totalDonated
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_donate, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_report -> {
                startActivity(Intent(this, Report::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        totalDonated = app.donationsStore.findAll().sumOf { it.amount }
        donateLayout.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
        donateLayout.progressBar.progress = totalDonated
    }
}