package com.engineblue.fuelprice.adapter.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuelprice.adapter.onboarding.holder.OnBoardingViewHolder
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.databinding.ItemContainerOnboardingBinding
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel

class OnBoardingAdapter(private val items: List<OnBoardingItemDisplayModel>) :
    RecyclerView.Adapter<OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder =
        OnBoardingViewHolder(
            ItemContainerOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

}