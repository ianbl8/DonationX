package com.ianbl8.donationx.ui.report

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ianbl8.donationx.R
import com.ianbl8.donationx.adapters.DonationAdapter
import com.ianbl8.donationx.adapters.DonationClickListener
import com.ianbl8.donationx.databinding.FragmentReportBinding
import com.ianbl8.donationx.main.DonationXApp
import com.ianbl8.donationx.models.DonationModel
import com.ianbl8.donationx.ui.auth.LoggedInViewModel
import com.ianbl8.donationx.utils.SwipeToDeleteCallback
import com.ianbl8.donationx.utils.SwipeToEditCallback
import com.ianbl8.donationx.utils.createLoader
import com.ianbl8.donationx.utils.hideLoader
import com.ianbl8.donationx.utils.showLoader
import timber.log.Timber

class ReportFragment : Fragment(), DonationClickListener {

    lateinit var app: DonationXApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val reportViewModel: ReportViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    lateinit var loader: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())

        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        showLoader(loader, "Downloading donations")
        reportViewModel.observableDonationsList.observe(viewLifecycleOwner, Observer { donations ->
            donations?.let {
                render(donations as ArrayList<DonationModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToDonateFragment()
            findNavController().navigate(action)
        }

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting donation")
                val adapter = fragBinding.recyclerView.adapter as DonationAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                reportViewModel.delete(
                    reportViewModel.liveFirebaseUser.value?.email!!,
                    (viewHolder.itemView.tag as DonationModel)._id
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onDonationClick(viewHolder.itemView.tag as DonationModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // super.onPrepareMenu(menu)
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_report, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(donationsList: ArrayList<DonationModel>) {
        fragBinding.recyclerView.adapter = DonationAdapter(donationsList, this)
        if (donationsList.isEmpty()) {
            Timber.i("donationsList is empty")
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.donationsNotFound.visibility = View.VISIBLE
        } else {
            Timber.i("donationsList is NOT empty")
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.donationsNotFound.visibility = View.GONE
        }
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading donations")
            reportViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onDonationClick(donation: DonationModel) {
        val action =
            ReportFragmentDirections.actionReportFragmentToDonationDetailFragment(donation._id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading donations")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                reportViewModel.liveFirebaseUser.value = firebaseUser
                reportViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}