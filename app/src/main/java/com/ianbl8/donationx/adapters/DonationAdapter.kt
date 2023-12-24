package com.ianbl8.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.donationx.R
import com.ianbl8.donationx.databinding.CardDonationBinding
import com.ianbl8.donationx.models.DonationModel
import com.ianbl8.donationx.utils.customTransformation
import com.squareup.picasso.Picasso

interface DonationClickListener {
    fun onDonationClick(donation: DonationModel)
}

class DonationAdapter constructor(
    private var donations: ArrayList<DonationModel>,
    private val listener: DonationClickListener,
    private val readOnly: Boolean
) :
    RecyclerView.Adapter<DonationAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding =
            CardDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding, readOnly)
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

    inner class MainHolder(val binding: CardDonationBinding, private val readOnly: Boolean) :
        RecyclerView.ViewHolder(binding.root) {
        val readOnlyRow = readOnly
        fun bind(donation: DonationModel, listener: DonationClickListener) {
            binding.root.tag = donation
            binding.donation = donation
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            Picasso.get().load(donation.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onDonationClick(donation) }
            binding.executePendingBindings()
        }
    }
}