package com.engineblue.fuelprice.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.adapter.fuel.StationAdapter
import com.engineblue.fuelprice.databinding.ConfigureLocationOptionFragmentBinding
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

// private fun requestLocationPermissions() {
//     /*ruestPermissions(
//         arrayOf(
//             Manifest.permission.ACCESS_FINE_LOCATION,
//             Manifest.permission.ACCESS_COARSE_LOCATION
//         ),
//         REQUEST_LOCATION_CODE
//     )
// }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == REQUEST_LOCATION_CODE) {
//            // Request for location permission.
//            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getCurrentLocation()
//            } else {
//                // Permission request was denied.
//                coordinatorLayout.showSnackbar(
//                    getString(R.string.location_permission_denied),
//                    Snackbar.LENGTH_SHORT
//                )
//            }
//        }
//    }

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

        checkLocationPermission()

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = StationAdapter()

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            layoutManager.orientation
        )

        dividerItemDecoration.setDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.divider_decoration
            )!!
        )

        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        binding.recyclerView.adapter = adapter
        viewModel.stationList.observe(viewLifecycleOwner, Observer { stations ->
            stations?.let {
                adapter.submitList(it)
                binding.loadingView.visibility = View.GONE
            }
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
//        when {
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//                    ContextCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) == PackageManager.PERMISSION_GRANTED
//            -> {
//                getCurrentLocation()
//            }
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) &&
//                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
//            -> {
//                coordinatorLayout.showSnackbar(
//                    getString(R.string.location_access_required),
//                    Snackbar.LENGTH_INDEFINITE,
//                    getString(R.string.ok)
//                ) { requestLocationPermissions() }
//            }
//            else -> {
//                requestLocationPermissions()
//            }
//        }
    }
}