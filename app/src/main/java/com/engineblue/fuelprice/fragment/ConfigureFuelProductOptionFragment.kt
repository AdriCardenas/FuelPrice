package com.engineblue.fuelprice.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.adapter.products.FuelProductAdapter
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.fragment.base.BaseFragment
import com.engineblue.presentation.entity.FuelProductDisplayModel
import com.engineblue.presentation.viewmodel.FuelViewModel
import kotlinx.android.synthetic.main.configure_fuel_product_option_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ConfigureFuelProductOptionFragment : BaseFragment(), SelectFuelProductListener {

    private var listener: SelectFuelProductListener? = null
    private val viewModel: FuelViewModel by sharedViewModel()

    companion object {
        fun newInstance() = ConfigureFuelProductOptionFragment()
    }

    override fun getLayoutRes(): Int = R.layout.configure_fuel_product_option_fragment

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        if (context is SelectFuelProductListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val adapter = FuelProductAdapter(this)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )

        dividerItemDecoration.setDrawable(
            getDrawable(
                requireContext(),
                R.drawable.divider_decoration
            )!!
        )

        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter
        viewModel.fuelProductList.observe(viewLifecycleOwner, Observer { products ->
            products?.let { adapter.submitList(it) }
        })

        viewModel.loadProducts()
    }

    override fun selectProduct(product: FuelProductDisplayModel) {
        if(product.id!=null && product.name!=null) {
            viewModel.saveProduct(product.id!!, product.name!!)
            listener?.selectProduct(product)
        }
    }

}