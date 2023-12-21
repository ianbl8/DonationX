package com.ianbl8.donationx.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ianbl8.donationx.databinding.FragmentDonationDetailBinding
import com.ianbl8.donationx.ui.auth.LoggedInViewModel
import com.ianbl8.donationx.ui.report.ReportViewModel

class DonationDetailFragment : Fragment() {

    private lateinit var detailViewModel: DonationDetailViewModel
    private val args by navArgs<DonationDetailFragmentArgs>()
    private var _fragBinding: FragmentDonationDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val reportViewModel: ReportViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDonationDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(DonationDetailViewModel::class.java)

        detailViewModel.observableDonation.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editDonationButton.setOnClickListener {
            detailViewModel.updateDonation(
                loggedInViewModel.liveFirebaseUser.value?.email!!,
                args.donationid,
                fragBinding.donationvm?.observableDonation!!.value!!
            )
            findNavController().navigateUp()
        }

        fragBinding.deleteDonationButton.setOnClickListener {
            reportViewModel.delete(
                loggedInViewModel.liveFirebaseUser.value?.uid!!,
                detailViewModel.observableDonation.value?.uid!!
            )
            findNavController().navigateUp()
        }

        return root
    }

    private fun render() {
        // fragBinding.editAmount.setText(donation.amount.toString())
        // fragBinding.editPaymenttype.setText(donation.paymentmethod)
        fragBinding.editMessage.setText("a message")
        fragBinding.editUpvotes.setText("0")
        fragBinding.donationvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getDonation(
            loggedInViewModel.liveFirebaseUser.value?.email!!,
            args.donationid
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}