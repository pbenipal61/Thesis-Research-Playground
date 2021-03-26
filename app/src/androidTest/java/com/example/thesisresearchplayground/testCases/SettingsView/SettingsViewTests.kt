package com.example.thesisresearchplayground.testCases.SettingsView

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.thesisresearchplayground.common.TestSet
import com.example.thesisresearchplayground.testViewModels.SettingsTestViewModel
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class SettingsViewTests: TestSet() {

    private val settingsViewModel: SettingsTestViewModel = SettingsTestViewModel()

    @Before
    override fun setUp(){
        super.setUp()
    }

    @After
    override fun tearDown(){
        super.tearDown()
    }

    @Test
    fun test_01_checkRandomSwitchExists() {
        settingsViewModel.goToSettingsView()
    }

}