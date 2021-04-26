package com.engineblue.fuelprice.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.engineblue.fuelprice.BuildConfig
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.activity.MainActivity
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.callback.SelectLocationListener
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


class ConfigurationLocationFragment : BaseFragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private var selectLocationListener: SelectLocationListener? = null
    private val viewModel: LocationViewModel by sharedViewModel()

    companion object {
        fun newInstance() = ConfigurationLocationFragment()
    }

    override fun getLayoutRes(): Int = R.layout.configure_location_option_fragment

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SelectLocationListener) {
            selectLocationListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_skip.setOnClickListener {

        }

        manualLocation.setOnClickListener {
            manualLocationField.visibility = View.VISIBLE
        }

        autoLocation.setOnClickListener {
            checkLocationPermission()
        }

        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            arrayListOf()
        )

        simpleMultiAutoCompleteTextView.setAdapter(adapter)
        simpleMultiAutoCompleteTextView.threshold = 1

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
                adapter.clear()
                address?.let {
                    viewModel.filterLocalities(it.toString(), Geocoder(requireContext()))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //NOTHING
            }

        })

        viewModel.localitiesFounded.observe(viewLifecycleOwner, {
            adapter.addAll(it)
        })

        viewModel.errorExceptionLocality.observe(viewLifecycleOwner, {
            context?.toast(it)
        })
    }

    private fun checkLocationPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        if (BuildConfig.DEBUG)
                            context?.toast("Permisos aceptados")

                        requestLocation()
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

    private fun requestLocation() {

    }


}