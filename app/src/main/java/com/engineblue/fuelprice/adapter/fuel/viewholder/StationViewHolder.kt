package com.engineblue.fuelprice.adapter.fuel.viewholder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import com.engineblue.fuelprice.adapter.viewholder.base.BaseViewHolder
import com.engineblue.fuelprice.databinding.StationListItemBinding
import com.engineblue.presentation.entity.StationDisplayModel
import java.text.DecimalFormat

class StationViewHolder(private val binding: StationListItemBinding) :
    BaseViewHolder<StationDisplayModel>(binding.root) {

    override fun bind(item: StationDisplayModel) {
        binding.stationName.text = getSpannableString(item.name, item.city)
        binding.stationAddress.text = item.address
        binding.stationDistance.text = "${formatDistance(distance = item.distance?.div(1000))} km"
        binding.stationFuelPrice.text = "${item.prize}€/L"

//        listener?.let{
//            itemView.setOnClickListener {
//                listener.selectProduct( item)
//            }
//        }
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
