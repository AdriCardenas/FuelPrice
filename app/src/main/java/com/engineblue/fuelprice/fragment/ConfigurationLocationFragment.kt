package com.engineblue.fuelprice.fragment

import android.Manifest
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView.CommaTokenizer
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.fragment.base.BaseFragment
import com.engineblue.fuelprice.utils.showSnackbar
import com.engineblue.fuelprice.utils.toast
import com.engineblue.presentation.entity.FuelProductDisplayModel
import com.engineblue.presentation.viewmodel.LocationViewModel
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.configure_location_option_fragment.*
import kotlinx.android.synthetic.main.station_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Exception


class ConfigurationLocationFragment : BaseFragment(), SelectFuelProductListener {

    private var listener: SelectFuelProductListener? = null
    private val viewModel: LocationViewModel by sharedViewModel()

    companion object {
        fun newInstance() = ConfigurationLocationFragment()
    }

    override fun getLayoutRes(): Int = R.layout.configure_location_option_fragment


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manualLocation.setOnClickListener {
            manualLocationField.visibility = View.VISIBLE
        }

        autoLocation.setOnClickListener {
            checkLocationPermission()
        }



        simpleMultiAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //NOTHING
            }

            override fun onTextChanged(
                address: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                address?.let {
                    try {
                        val listAddress = Geocoder(requireContext()).getFromLocationName(
                            address.toString(),
                            3
                        )

                        val arrayList = arrayListOf<String>()

                        if (!listAddress.isNullOrEmpty()) {
                            for(item in listAddress){
                                arrayList.add(
                                    item.locality + ", " + item.adminArea + ", " + item.countryName
                                )
                            }
                        }

                        val locations = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            arrayList
                        )

                        simpleMultiAutoCompleteTextView.setAdapter(locations)

                        simpleMultiAutoCompleteTextView.threshold = 3
                        simpleMultiAutoCompleteTextView.setTokenizer(CommaTokenizer())
                    } catch (
                        exception:Exception
                    ){
                        exception.localizedMessage?.let {
                            context?.toast(it)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //NOTHING
            }

        })

//
//        viewModel.fuelProductList.observe(viewLifecycleOwner, Observer { products ->
//            products?.let { adapter.submitList(it) }
//        })
//
//        viewModel.loadProducts()
    }

    private fun checkLocationPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        context?.toast("Permisos aceptados")
                    } else {
                        // Permission request was denied.
                        coordinatorLayout.showSnackbar(
                            getString(R.string.location_permission_denied),
                            Snackbar.LENGTH_SHORT
                        )
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    //TODO MANUAL LOCATION
                    context?.toast(R.string.location_permission_denied)
                }
            })
            .withErrorListener {
                context?.toast(it.name)
            }.check()
    }

    override fun selectProduct(product: FuelProductDisplayModel) {
//        if(product.id!=null && product.name!=null) {
//            viewModel.saveProduct(product.id!!, product.name!!)
//            listener?.selectProduct(product)
//        }
    }


}