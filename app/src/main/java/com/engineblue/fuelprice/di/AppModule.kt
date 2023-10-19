package com.engineblue.fuelprice.di

import com.engineblue.data.datasource.FuelApi
import com.engineblue.data.datasource.PreferenceSettings
import com.engineblue.data.repository.FuelRepositoryImpl
import com.engineblue.data.repository.SettingRepositoryImpl
import com.engineblue.data.repository.StationsRepositoryImpl
import com.engineblue.domain.repository.FuelRepository
import com.engineblue.domain.repository.SettingRepository
import com.engineblue.domain.repository.StationsRepository
import com.engineblue.domain.useCasesContract.GetRemoteProducts
import com.engineblue.domain.useCasesContract.GetRemoteStations
import com.engineblue.domain.useCasesContract.SaveLocationSelected
import com.engineblue.domain.useCasesContract.preferences.GetSavedBoolean
import com.engineblue.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.domain.useCasesContract.preferences.SaveBoolean
import com.engineblue.domain.useCasesContract.preferences.SaveProductSelected
import com.engineblue.fuelprice.network.createNetworkClient
import com.engineblue.fuelprice.preferences.PreferenceManager
import com.engineblue.fuelprice.utils.AndroidLoggingHandler
import com.engineblue.presentation.useCases.*
import com.engineblue.presentation.useCases.preferences.GetSavedBooleanImpl
import com.engineblue.presentation.useCases.preferences.SaveBooleanImpl
import com.engineblue.presentation.viewmodel.FuelViewModel
import com.engineblue.presentation.viewmodel.ListStationsViewModel
import com.engineblue.presentation.viewmodel.OnBoardingViewModel
import com.engineblue.presentation.viewmodel.RoutingViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.logging.Logger

val mRepositoryModules = module {
    single<FuelRepository> {
        FuelRepositoryImpl(
            api = get(),
            logger = get(),
            settingsDataSource = get()
        )
    }

    single<StationsRepository> { StationsRepositoryImpl(api = get(), logger = get()) }
    single<SettingRepository> { SettingRepositoryImpl(settingsDataSource = get()) }
}

val mUseCaseModules = module {
    factory<GetRemoteProducts> {
        GetRemoteProductsImpl(
            repository = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory<GetRemoteStations> {
        GetRemoteStationsImpl(
            repository = get(),
            dispatcher = Dispatchers.IO
        )
    }

    factory<SaveProductSelected> {
        SaveProductSelectedImpl(
            repository = get()
        )
    }

    factory<GetSavedProduct> {
        GetSavedProductImpl(
            repository = get()
        )
    }

    factory<SaveLocationSelected> {
        SaveLocationSelectedImpl(
        )
    }

    factory<SaveBoolean> {
        SaveBooleanImpl(
            settingRepository = get()
        )
    }

    factory<GetSavedBoolean> {
        GetSavedBooleanImpl(
            settingRepository = get()
        )
    }
}

val mNetworkModules = module {
    single(named(name = RETROFIT_INSTANCE)) { createNetworkClient(BASE_URL, androidContext()) }
    single<FuelApi> { get<Retrofit>(named(name = RETROFIT_INSTANCE)).create(FuelApi::class.java) }
}

val mViewModels = module {
    viewModel {
        FuelViewModel(getRemoteProducts = get(), saveProductSelected = get())
    }

    viewModel {
        ListStationsViewModel(
            getRemoteStations = get(),
            getSavedProduct = get()
        )
    }

    viewModel {
        OnBoardingViewModel(
            saveBooleanPreference = get()
        )
    }

    viewModel {
        RoutingViewModel(
            getSavedBoolean = get(),
            getSavedProduct = get(),
            saveBooleanPreference = get()
        )
    }
}

val mUtils = module {
    AndroidLoggingHandler.setup()
    single<Logger> { Logger.getLogger("AppLogger") }
}

val mPreferences = module {
    single<PreferenceSettings> {
        PreferenceManager(context = androidContext())
    }
}

private const val BASE_URL =
    "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/"
private const val RETROFIT_INSTANCE = "Retrofit"