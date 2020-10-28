package com.engineblue.fuelprice.adapter.onboarding.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel
import kotlinx.android.synthetic.main.item_container_onboarding.view.*

class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: OnBoardingItemDisplayModel){
        itemView.titleOnBoarding.text = item.title
        itemView.descriptionOnBoarding.text = item.description
        itemView.imageOnBoarding.setImageResource(item.imageResource)
    }
}