package com.engineblue.fuelprice.adapter.onboarding.holder

import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuelprice.databinding.ItemContainerOnboardingBinding
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel

class OnBoardingViewHolder(private val binding:ItemContainerOnboardingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: OnBoardingItemDisplayModel){
        binding.titleOnBoarding.text = item.title
        binding.descriptionOnBoarding.text = item.description
        binding.imageOnBoarding.setImageResource(item.imageResource)
    }
}