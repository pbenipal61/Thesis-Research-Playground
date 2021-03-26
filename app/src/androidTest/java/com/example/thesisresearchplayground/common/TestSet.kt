package com.example.thesisresearchplayground.common

open class TestSet {

    val utilities: Utilities = Utilities()

    open fun setUp() {
        println("Setting test up")
        launchApp()
    }

    open fun tearDown() {
        println("Tearing down")
    }

    fun launchApp() {
        utilities.launchApp(utilities.getPackageName())
    }
}