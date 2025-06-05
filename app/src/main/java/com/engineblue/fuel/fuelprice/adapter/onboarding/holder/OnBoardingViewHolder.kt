package com.engineblue.fuel.fuelprice.adapter.onboarding.holder

import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel
import com.fuel.engineblue.databinding.ItemContainerOnboardingBinding

class OnBoardingViewHolder(private val binding: ItemContainerOnboardingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: OnBoardingItemDisplayModel){
        binding.titleOnBoarding.text = item.title
        binding.descriptionOnBoarding.text = item.description
        binding.imageOnBoarding.setImageResource(item.imageResource)
    }
}