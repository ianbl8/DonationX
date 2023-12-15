package com.ianbl8.donationx.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianbl8.donationx.R
import com.ianbl8.donationx.adapters.DonationAdapter
import com.ianbl8.donationx.databinding.ActivityReportBinding
import com.ianbl8.donationx.main.DonationXApp

class Report : AppCompatActivity() {
    lateinit var app: DonationXApp
    lateinit var reportLayout: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportLayout = ActivityReportBinding.inflate(layoutInflater)
        setContentView(reportLayout.root)

        app = this.application as DonationXApp
        reportLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        reportLayout.recyclerView.adapter = DonationAdapter(app.donationsStore.findAll())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_donate -> {
                startActivity(Intent(this, Donate::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}