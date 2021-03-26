package com.example.thesisresearchplayground.di.module

import com.example.thesisresearchplayground.ui.main.viewmodel.LandingViewModel
import com.example.thesisresearchplayground.ui.main.viewmodel.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel(get(), get())
    }
    viewModel {
        LandingViewModel(androidApplication())
    }
}