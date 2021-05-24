package com.engineblue.fuelprice.adapter.products.viewholder

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import com.engineblue.fuelprice.adapter.viewholder.base.BaseViewHolder
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.databinding.FuelProductListItemBinding
import com.engineblue.presentation.entity.FuelProductDisplayModel

class FuelProductViewHolder(
    private val binding: FuelProductListItemBinding,
    private val listener: SelectFuelProductListener?
) : BaseViewHolder<FuelProductDisplayModel>(binding.root) {

    override fun bind(item: FuelProductDisplayModel) {
        binding.name.text = getSpannableString(item.name, item.nameAbbreviature)

        listener?.let {
            itemView.setOnClickListener {
                listener.selectProduct(item)
            }
        }
    }

    private fun getSpannableString(name: String?, nameAbbreviature: String?): SpannableString {
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
