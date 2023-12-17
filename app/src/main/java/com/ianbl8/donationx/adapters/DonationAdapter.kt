package com.ianbl8.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.donationx.R
import com.ianbl8.donationx.databinding.CardDonationBinding
import com.ianbl8.donationx.models.DonationModel

interface DonationClickListener {
    fun onDonationClick(donation: DonationModel)
}

class DonationAdapter constructor(
    private var donations: ArrayList<DonationModel>,
    private val listener: DonationClickListener
) :
    RecyclerView.Adapter<DonationAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding =
            CardDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = donations[holder.adapterPosition]
        holder.bind(donation, listener)
    }

    override fun getItemCount(): Int = donations.size

    fun removeAt(position: Int) {
        donations.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MainHolder(val binding: CardDonationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donation: DonationModel, listener: DonationClickListener) {
            binding.root.tag = donation._id
            binding.donation = donation
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onDonationClick(donation) }
            binding.executePendingBindings()
        }
    }
}