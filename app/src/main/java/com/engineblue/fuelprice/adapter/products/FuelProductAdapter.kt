package com.engineblue.fuelprice.adapter.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.adapter.products.viewholder.FuelProductViewHolder
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.databinding.FuelProductListItemBinding
import com.engineblue.presentation.entity.FuelProductDisplayModel


class FuelProductAdapter(private val listener: SelectFuelProductListener?) :
    ListAdapter<FuelProductDisplayModel, FuelProductViewHolder>(
        object :
            DiffUtil.ItemCallback<FuelProductDisplayModel>() {
            override fun areItemsTheSame(
                oldItem: FuelProductDisplayModel,
                newItem: FuelProductDisplayModel
            ) =
                oldItem.areItemsTheSame(newItem)

            override fun areContentsTheSame(
                oldItem: FuelProductDisplayModel,
                newItem: FuelProductDisplayModel
            ) =
                oldItem.areContentsTheSame(newItem)
        }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelProductViewHolder =
        LayoutInflater.from(parent.context).run {
            FuelProductViewHolder(
                FuelProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                listener
            )
        }


    override fun onBindViewHolder(holder: FuelProductViewHolder, position: Int) =
        holder.bind(getItem(position))
}