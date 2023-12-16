package com.ianbl8.donationx.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ianbl8.donationx.R

class AboutusFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aboutus, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutusFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}