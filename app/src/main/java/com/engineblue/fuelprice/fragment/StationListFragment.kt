package com.engineblue.fuelprice.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.activity.ConfigurationActivity
import com.engineblue.fuelprice.adapter.fuel.StationAdapter
import com.engineblue.fuelprice.databinding.StationListFragmentBinding
import com.engineblue.fuelprice.fragment.base.BaseFragment
import com.engineblue.fuelprice.utils.showSnackbar
import com.engineblue.fuelprice.utils.toast
import com.engineblue.presentation.viewmodel.ListStationsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class StationListFragment : BaseFragment() {

    private var location: Location? = null
    private val viewModel: ListStationsViewModel by sharedViewModel()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var binding: StationListFragmentBinding

    companion object {
        fun newInstance() = StationListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StationListFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                this.location = location


            }.addOnCompleteListener {
                if (it.isSuccessful) {
                    this.location = it.result
                    viewModel.loadStations()

                    viewModel.latitude = location?.latitude
                    viewModel.longitude = location?.longitude
                } else {
                    viewModel.loadStations()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.setting_fragment) {
                startActivity(Intent(requireContext(), ConfigurationActivity::class.java))
            }
            false
        }

        val adapter = initializeRecycler()
        subscribeToViewModel(adapter)

        checkLocationPermission()
    }

    private fun initializeRecycler(): StationAdapter {
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = StationAdapter()

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                layoutManager.orientation
            )
        )

        binding.recyclerView.adapter = adapter
        return adapter
    }

    private fun subscribeToViewModel(adapter: StationAdapter) {
        viewModel.stationList.observe(viewLifecycleOwner) { stations ->
            stations?.let {
                adapter.submitList(it)
                binding.loadingView.visibility = View.GONE
            }
        }

        viewModel.selectedFuel.observe(viewLifecycleOwner) { fuel ->
            fuel?.let {
                binding.toolbar.subtitle = fuel.name
            }
        }
    }

    private fun checkLocationPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        getCurrentLocation()
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
}

//                    if (location?.latitude != null && location?.longitude != null) {
//                        val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
//
//                        val addresses: List<Address> = geocoder.getFromLocation(
//                            location!!.latitude,
//                            location!!.longitude,
//                            1
//                        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//
//                        val address: String =
//                            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//
//                        val city: String = addresses[0].getLocality()
//                        val state: String = addresses[0].getAdminArea()
//                        val country: String = addresses[0].getCountryName()
//
//                        Log.d("POSITION**", "$address $city $state $country $postalCode $knownName")
//                    }