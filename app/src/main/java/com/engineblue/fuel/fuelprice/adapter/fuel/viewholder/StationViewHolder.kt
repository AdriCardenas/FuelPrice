package com.engineblue.fuel.fuelprice.adapter.fuel.viewholder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.engineblue.fuel.fuelprice.adapter.fuel.StationAdapter
import com.engineblue.fuel.fuelprice.adapter.viewholder.base.BaseViewHolder
import com.fuel.engineblue.R
import com.fuel.engineblue.databinding.StationListItemBinding
import java.text.DecimalFormat

class StationViewHolder(
    private val binding: StationListItemBinding,
    private val stationClickListener: StationAdapter.ClickListener
) :
    BaseViewHolder<com.engineblue.fuel.presentation.entity.StationDisplayModel>(binding.root) {

    override fun bind(item: com.engineblue.fuel.presentation.entity.StationDisplayModel) {
        binding.stationName.text = getSpannableString(item.name, item.city)
        binding.stationAddress.text = item.address
        binding.stationDistance.text = "${formatDistance(distance = item.distance?.div(1000))} km"
        binding.stationFuelPrice.text = "${item.price}€/L"

        when (item.priceStatus) {
            com.engineblue.fuel.presentation.entity.StationDisplayModel.PriceStatus.CHEAP -> {
                binding.priceStatus.visibility = View.VISIBLE
                binding.priceStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.cheap_price_rectangle
                    )
                )
            }
            com.engineblue.fuel.presentation.entity.StationDisplayModel.PriceStatus.REGULAR -> {
                binding.priceStatus.visibility = View.VISIBLE
                binding.priceStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.regular_price_rectangle
                    )
                )
            }
            com.engineblue.fuel.presentation.entity.StationDisplayModel.PriceStatus.EXPENSIVE -> {
                binding.priceStatus.visibility = View.VISIBLE
                binding.priceStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.expensive_price_rectangle
                    )
                )
            }
            else -> binding.priceStatus.visibility = View.GONE
        }

        itemView.setOnClickListener {
            stationClickListener.onClick(item)
        }
    }

    private fun formatDistance(distance: Float?): String {
        if (distance == null) return "?"

        return try {
            val precision = DecimalFormat("0.0")
            precision.format(distance)
        } catch (e: NumberFormatException) {
            "?"
        }
    }

    private fun getSpannableString(
        name: String?,
        nameAbbreviature: String?
    ): SpannableString {
        name?.let {
            val spannable = SpannableString("$name - $nameAbbreviature")

            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0, name.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            return spannable
        }

        return SpannableString("")
    }
}
