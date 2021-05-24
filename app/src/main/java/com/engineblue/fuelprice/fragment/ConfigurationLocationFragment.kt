package com.engineblue.fuelprice.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.engineblue.fuelprice.BuildConfig
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.callback.SelectLocationListener
import com.engineblue.fuelprice.databinding.ConfigureLocationOptionFragmentBinding
import com.engineblue.fuelprice.fragment.base.BaseFragment
import com.engineblue.fuelprice.utils.showSnackbar
import com.engineblue.fuelprice.utils.toast
import com.engineblue.presentation.viewmodel.LocationViewModel
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ConfigurationLocationFragment : BaseFragment() {

    private lateinit var adapter: ArrayAdapter<String>
    private var selectLocationListener: SelectLocationListener? = null
    private val locationManager: LocationManager? by lazy {
        requireContext().getSystemService(LOCATION_SERVICE) as LocationManager?
    }

    private val viewModel: LocationViewModel by sharedViewModel()

    private lateinit var binding: ConfigureLocationOptionFragmentBinding

    companion object {
        fun newInstance() = ConfigurationLocationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConfigureLocationOptionFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SelectLocationListener) {
            selectLocationListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSkip.setOnClickListener {
            selectLocationListener?.onLocationSelected()
        }

        binding.manualLocation.setOnClickListener {
            binding.manualLocationField.visibility = View.VISIBLE
        }

        binding.autoLocation.setOnClickListener {
            checkLocationPermission()
        }

        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            arrayListOf()
        )

        binding.simpleMultiAutoCompleteTextView.setAdapter(adapter)
        binding.simpleMultiAutoCompleteTextView.threshold = 1

        binding.simpleMultiAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
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
                        binding.coordinatorLayout.showSnackbar(
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

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 10, 1f
        ) { location -> viewModel.saveLocation(location) }
    }


}