package com.engineblue.fuelprice.adapter.products.viewholder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import com.engineblue.fuelprice.adapter.viewholder.base.BaseViewHolder
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.presentation.entity.FuelProductDisplayModel
import kotlinx.android.synthetic.main.fuel_product_list_item.view.*

class FuelProductViewHolder(view: View, private val listener: SelectFuelProductListener?) : BaseViewHolder<FuelProductDisplayModel>(view) {

    override fun bind(item: FuelProductDisplayModel) {
        itemView.name.text = getSpannableString(item.name, item.nameAbbreviature)

        listener?.let{
            itemView.setOnClickListener {
                listener.selectProduct(item)
            }
        }
    }

    private fun getSpannableString(name: String?, nameAbbreviature: String?): SpannableString? {
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
