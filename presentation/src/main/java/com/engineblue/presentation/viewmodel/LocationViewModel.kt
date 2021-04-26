package com.engineblue.presentation.viewmodel

import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.engineblue.domain.useCasesContract.SaveLocationSelected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.Exception

class LocationViewModel
    (
    private val saveLocationSelected: SaveLocationSelected,
    scope: ViewModelScope = viewModelScope()
) : ViewModel(), ViewModelScope by scope {

    val errorExceptionLocality = MutableLiveData<String>()
    val localitiesFounded = MutableLiveData<List<String>>()

    fun filterLocalities(filter: String, geocoder: Geocoder) {
        launch(Dispatchers.IO) {
            try {
                val listAddress = geocoder.getFromLocationName(
                    filter,
                    1
                )

                val arrayList = arrayListOf<String>()

                if (!listAddress.isNullOrEmpty()) {
                    for (item in listAddress) {
                        if (item.countryCode == "ES")
                            arrayList.add(
                                item.locality + ", " + item.adminArea + ", " + item.countryName
                            )
                    }
                }

                localitiesFounded.run {
                    postValue(arrayList)
                }

            } catch (
                exception: Exception
            ) {

                exception.localizedMessage?.let {
                    errorExceptionLocality.postValue(it)
                }
            }
        }
    }
}