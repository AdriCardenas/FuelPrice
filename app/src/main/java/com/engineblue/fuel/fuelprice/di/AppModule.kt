package com.engineblue.fuel.fuelprice.di

import com.engineblue.fuel.data.datasource.FuelApi
import com.engineblue.fuel.data.datasource.PreferenceSettings
import com.engineblue.fuel.data.repository.FuelRepositoryImpl
import com.engineblue.fuel.data.repository.SettingRepositoryImpl
import com.engineblue.fuel.data.repository.StationsRepositoryImpl
import com.engineblue.fuel.domain.repository.FuelRepository
import com.engineblue.fuel.domain.repository.SettingRepository
import com.engineblue.fuel.domain.repository.StationsRepository
import com.engineblue.fuel.domain.useCasesContract.GetRemoteHistoricByDateCityAndProduct
import com.engineblue.fuel.domain.useCasesContract.GetRemoteProducts
import com.engineblue.fuel.domain.useCasesContract.GetRemoteStations
import com.engineblue.fuel.domain.useCasesContract.SaveLocationSelected
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedBoolean
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedProduct
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveProductSelected
import com.engineblue.fuel.fuelprice.network.createNetworkClient
import com.engineblue.fuel.fuelprice.preferences.PreferenceManager
import com.engineblue.fuel.presentation.useCases.*
import com.engineblue.fuel.presentation.useCases.preferences.SaveBooleanImpl
import com.engineblue.fuel.presentation.viewmodel.FuelViewModel
import com.engineblue.fuel.presentation.viewmodel.ListStationsViewModel
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

    factory<GetRemoteHistoricByDateCityAndProduct> {
        GetRemoteHistoricByDateCityAndProductImpl(
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
        com.engineblue.fuel.presentation.useCases.preferences.GetSavedBooleanImpl(
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
            getSavedProduct = get(),
            getRemoteHistoricByDateCityAndProduct = get()
        )
    }

    viewModel {
        com.engineblue.fuel.presentation.viewmodel.OnBoardingViewModel(
            saveBooleanPreference = get()
        )
    }

    viewModel {
        com.engineblue.fuel.presentation.viewmodel.RoutingViewModel(
            getSavedBoolean = get(),
            getSavedProduct = get()
        )
    }
}

val mUtils = module {
    com.engineblue.fuel.fuelprice.utils.AndroidLoggingHandler.setup()
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