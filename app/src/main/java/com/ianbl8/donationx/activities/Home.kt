package com.ianbl8.donationx.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.*
import androidx.navigation.ui.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.donationx.R
import com.ianbl8.donationx.databinding.HomeBinding

class Home : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var homeBinding: HomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = HomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        drawerLayout = homeBinding.drawerLayout

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.donateFragment,
                R.id.reportFragment,
                R.id.aboutUsFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = homeBinding.navView
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}