package com.ianbl8.donationx.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ianbl8.donationx.R

class AboutusFragment: Fragment() {

    private lateinit var aboutusViewModel: AboutusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutusViewModel = ViewModelProvider(this).get(AboutusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_aboutus, container, false)
        aboutusViewModel.text.observe(viewLifecycleOwner, Observer { })
        return root
    }

}