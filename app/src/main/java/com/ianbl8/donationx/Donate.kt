package com.ianbl8.donationx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ianbl8.donationx.databinding.ActivityDonateBinding
import timber.log.Timber

class Donate : AppCompatActivity() {
    private lateinit var donateLayout: ActivityDonateBinding
    var totalDonated = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        donateLayout = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(donateLayout.root)

        donateLayout.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
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
                totalDonated += amount
                Timber.i("Total donated so far: \$$totalDonated")
                donateLayout.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
                donateLayout.progressBar.progress = totalDonated
            }
        }
    }
}