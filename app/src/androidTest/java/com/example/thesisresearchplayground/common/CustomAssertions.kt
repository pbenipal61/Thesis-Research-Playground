package com.example.thesisresearchplayground.common

import android.view.View
import org.junit.Assert

abstract class CustomAssertions {

    fun waitAndAssertVisibility(waitTime: Long = 0, view: View) {
        Thread.sleep(waitTime)
        if (view.visibility == View.VISIBLE) {
            Assert.assertTrue(true)
        } else {
            Assert.fail()
        }
    }
}