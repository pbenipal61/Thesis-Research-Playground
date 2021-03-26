package com.example.thesisresearchplayground.testViewModels

import com.example.thesisresearchplayground.R
import com.example.thesisresearchplayground.common.TestViewModel
import com.example.thesisresearchplayground.common.Utilities

class SettingsTestViewModel: TestViewModel(){

    val sideMenuViewModel: SideMenuViewModel = SideMenuViewModel()

    val switchViewId: Int = R.id.random_switch

    fun goToSettingsView() {
        utilities.waitFor(3000)
        utilities.swipeScreen(Utilities.SwipeScreenDirection.RIGHT)
    }
}