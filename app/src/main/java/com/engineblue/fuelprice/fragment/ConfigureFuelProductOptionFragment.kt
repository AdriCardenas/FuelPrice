package com.engineblue.fuelprice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.adapter.products.FuelProductAdapter
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.databinding.ConfigureFuelProductOptionFragmentBinding
import com.engineblue.fuelprice.fragment.base.BaseFragment
import com.engineblue.presentation.entity.FuelProductDisplayModel
import com.engineblue.presentation.viewmodel.FuelViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ConfigureFuelProductOptionFragment : BaseFragment(), SelectFuelProductListener {

    private val viewModel: FuelViewModel by sharedViewModel()

    private lateinit var binding: ConfigureFuelProductOptionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            ConfigureFuelProductOptionFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = FuelProductAdapter(this)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                layoutManager.orientation
            )
        )

        binding.recyclerView.adapter = adapter
//        viewModel.fuelProductList(viewLifecycleOwner) { products ->
//            products?.let { adapter.submitList(it) }
//        }

//        viewModel.loadProducts()
    }

    override fun selectProduct(product: FuelProductDisplayModel) {
        if (product.id != null && product.name != null) {
            viewModel.saveProduct(product.id!!, product.name!!)
            findNavController().navigate(R.id.action_configuration_fuel_product_to_list_stations)
        }
    }

}