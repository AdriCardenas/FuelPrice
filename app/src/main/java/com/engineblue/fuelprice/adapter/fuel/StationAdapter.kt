package com.engineblue.fuelprice.adapter.fuel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.engineblue.fuelprice.adapter.fuel.viewholder.StationViewHolder
import com.engineblue.fuelprice.databinding.StationListItemBinding
import com.engineblue.presentation.entity.StationDisplayModel

class StationAdapter(private val stationClickListener: ClickListener) :
    ListAdapter<StationDisplayModel, StationViewHolder>(
        object :
            DiffUtil.ItemCallback<StationDisplayModel>() {
            override fun areItemsTheSame(
                oldItem: StationDisplayModel,
                newItem: StationDisplayModel
            ) =
                oldItem.areItemsTheSame(newItem)

            override fun areContentsTheSame(
                oldItem: StationDisplayModel,
                newItem: StationDisplayModel
            ) =
                oldItem.areContentsTheSame(newItem)
        }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder =
        LayoutInflater.from(parent.context).run {
            StationViewHolder(
                StationListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), stationClickListener
            )
        }


    override fun onBindViewHolder(holder: StationViewHolder, position: Int) =
        holder.bind(getItem(position))

    interface ClickListener {
        fun onClick(station: StationDisplayModel)
    }
}