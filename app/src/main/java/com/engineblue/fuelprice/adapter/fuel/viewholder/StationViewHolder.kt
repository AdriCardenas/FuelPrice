package com.engineblue.fuelprice.adapter.fuel.viewholder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import com.engineblue.fuelprice.adapter.viewholder.base.BaseViewHolder
import com.engineblue.presentation.entity.StationDisplayModel
import kotlinx.android.synthetic.main.station_list_item.view.*
import java.text.DecimalFormat

class StationViewHolder(view: View) : BaseViewHolder<StationDisplayModel>(view) {

    override fun bind(item: StationDisplayModel) {
        itemView.stationName.text = getSpannableString(item.name, item.city)
        itemView.stationAddress.text = item.address
        itemView.stationDistance.text = "${formatDistance(distance = item.distance?.div(1000))} km"
        itemView.stationFuelPrice.text = "${item.prize}€/L"

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
    ): SpannableString? {
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
