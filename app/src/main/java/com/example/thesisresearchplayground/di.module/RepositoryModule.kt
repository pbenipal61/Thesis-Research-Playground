package com.example.thesisresearchplayground.di.module

import com.example.thesisresearchplayground.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single { MainRepository(get()) }
}