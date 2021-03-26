package com.example.thesisresearchplayground.testViewModels

import android.view.MenuItem
import android.view.View
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiObject2
import com.example.thesisresearchplayground.R
import com.example.thesisresearchplayground.common.TestViewModel
import org.junit.Assert

class SideMenuViewModel: TestViewModel() {

    val settingsMenuItemId: Int = R.id.nav_settingsFragment

    fun getItemObjects(): ArrayList<UiObject2> {
        return utilities.getDevice().findObject(
            By.clazz("android.widget.ListView")
        ).children as ArrayList<UiObject2>
    }

    fun tapOnSettings(view: View) {
        val settingsMenuItemButton: UiObject2 = getItemObjects()[2]
        if (settingsMenuItemButton.isClickable) {
            settingsMenuItemButton.click()
            settingsMenuItemButton.recycle()
        } else {
            Assert.fail()
        }

    }
}