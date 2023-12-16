package com.ianbl8.donationx.ui.donate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ianbl8.donationx.R
import com.ianbl8.donationx.databinding.FragmentDonateBinding
import com.ianbl8.donationx.main.DonationXApp
import com.ianbl8.donationx.models.DonationModel
import timber.log.Timber

class DonateFragment : Fragment() {

    lateinit var app: DonationXApp
    var totalDonated = 0
    private var _fragBinding: FragmentDonateBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var donateViewModel: DonateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as DonationXApp
        // setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDonateBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_donate)

        setupMenu()
        donateViewModel = ViewModelProvider(this).get(DonateViewModel::class.java)
        donateViewModel.text.observe(viewLifecycleOwner, Observer { })

        fragBinding.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
        fragBinding.progressBar.progress = totalDonated
        fragBinding.progressBar.max = 10000
        fragBinding.amountPicker.minValue = 1
        fragBinding.amountPicker.maxValue = 1000

        fragBinding.amountPicker.setOnValueChangedListener { _, _, newVal ->
            fragBinding.paymentAmount.setText("$newVal")
        }

        setButtonListener(this.fragBinding)
        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // super.onPrepareMenu(menu)
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_donate, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(menuItem, requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        totalDonated = app.donationsStore.findAll().sumOf { it.amount }
        fragBinding.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
        fragBinding.progressBar.progress = totalDonated
    }

    fun setButtonListener(layout: FragmentDonateBinding) {
        layout.donateButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty()) {
                layout.paymentAmount.text.toString().toInt()
            } else {
                layout.amountPicker.value
            }
            if (totalDonated >= layout.progressBar.max) {
                Toast.makeText(context, "Donate amount exceeded!", Toast.LENGTH_LONG).show()
            } else {
                val paymentmethod =
                    if (layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                Timber.i("Total donated so far: \$$totalDonated")
                app.donationsStore.create(
                    DonationModel(
                        paymentmethod = paymentmethod,
                        amount = amount
                    )
                )
                layout.totalSoFar.text = getString(R.string.totalSoFar, totalDonated)
                layout.progressBar.progress = totalDonated
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DonateFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}