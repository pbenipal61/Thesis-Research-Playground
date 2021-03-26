package com.example.thesisresearchplayground.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.junit.Assert
import java.io.File

private val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

data class packageList(var custom: String, var packageName: String)

class Utilities {
    private val instrumentationContext: Context by lazy {
        InstrumentationRegistry.getInstrumentation().context }


    fun waitFor(waitFor: Long) {
        getDevice().waitForIdle(waitFor)
    }

    fun getDevice(): UiDevice {
        return mDevice
    }

    fun getPackageName(): String {
        return "com.example.thesisresearchplayground"
    }

    fun waitForUIText(
        text: Any,
        wait: Int = 10,
        click: Boolean = false,
        search: Boolean = false,
        partialText: Boolean = false,
        searchLiteMode: Boolean = false
    ): Boolean {
        var string = text.toString()
        if (text is Int) {
            string = instrumentationContext.getString(text)
        }
        var result: Boolean = false
        val endTime: Long = System.currentTimeMillis() + (wait * 1000)
        while (endTime > System.currentTimeMillis()) {
            try {
                if (search) {
                    if (partialText) {
                        searchForPartialText(string)
                        Assert.assertTrue(
                            mDevice.findObject(
                                UiSelector()
                                    .textContains(string)
                            ).exists()
                        )
                    } else {
                        searchForText(string)
                        Assert.assertTrue(
                            mDevice.findObject(
                                UiSelector()
                                    .text(string)
                            ).exists()
                        )
                    }
                } else {
                    if (partialText) {
                        Assert.assertTrue(
                            mDevice.findObject(
                                UiSelector()
                                    .textContains(string)
                            ).exists())
                    } else {
                        Assert.assertTrue(
                            mDevice.findObject(
                                UiSelector()
                                    .text(string)
                            ).exists()
                        )
                    }
                }
                if (click) {
                    if (partialText) {
                        Assert.assertTrue(
                            mDevice.findObject(
                                UiSelector()
                                    .textContains(string)
                            ).click()
                        )
                    } else {
                        Assert.assertTrue(
                            mDevice.findObject(
                                UiSelector()
                                    .text(string)
                            ).click()
                        )
                    }
                }
                return true
            } catch (e: java.lang.Exception) {
                Thread.sleep(500)
            } catch (e: Throwable) {
                Thread.sleep(500)
            }
        }
        return result
    }


    fun openYoutubeLink(youtubeID: String) {
        val context = InstrumentationRegistry.getInstrumentation().context
        var intent = context.packageManager.getLaunchIntentForPackage("com.google.android.youtube")
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        intentApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val intentBrowser = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "http://www.youtube.com/watch?v=" + youtubeID
            )
        )
        intentBrowser.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentBrowser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(intentBrowser)
        }
    }

    fun waitForResourceId(id: String, wait: Int = 10, click: Boolean = false): Boolean {
        //  val resourceID = BuildConfig.APPLICATION_ID + ":id/" + id
        val resourceID = id
        var mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val endTime: Long = System.currentTimeMillis() + (wait * 1000)
        while (endTime > System.currentTimeMillis()) {
            try {
                if (mDevice.findObject(UiSelector().resourceId(resourceID)).exists()) {
                    if (click) {
                        mDevice.findObject(UiSelector().resourceId(resourceID)).click()
                    }
                    return true
                }
            } catch (e: androidx.test.uiautomator.UiObjectNotFoundException) {
                Thread.sleep(100)
                continue
            }
        }
        return false
    }

    enum class SwipeScreenDirection {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }

    fun swipeScreen(direction: SwipeScreenDirection) {
        when (direction) {
            SwipeScreenDirection.DOWN -> {
                mDevice.pressDPadDown()
            }
            SwipeScreenDirection.UP -> {
                mDevice.pressDPadUp()
            }
            SwipeScreenDirection.RIGHT -> {
                mDevice.swipe(100, 500, 531, 500, 20)
            }
            SwipeScreenDirection.LEFT -> {
                mDevice.swipe(531, 500, 100, 500, 20)
            }
        }
        // We need to wait until swipe action has been finished.
        Thread.sleep(1000)
    }

    fun searchForText(text: String): Boolean {
        val textScroll = UiScrollable(UiSelector().scrollable(true))
        return (textScroll.scrollIntoView(UiSelector().text(text)))
    }

    fun searchForPartialText(text: String): Boolean {
        val textScroll = UiScrollable(UiSelector().scrollable(true))
        return (textScroll.scrollIntoView(UiSelector().textContains(text)))
    }


    fun turnWiFi(enable: Boolean) {
        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent, null)
        Assert.assertTrue(waitForResourceId(
            "com.android.settings:id/switch_widget"))
        var switch = mDevice.findObject(UiSelector().resourceId(
            "com.android.settings:id/switch_widget"))
        if (switch.isChecked != enable) {
            switch.click()
            val endTime: Long = System.currentTimeMillis() + (5 * 1000)
            while (endTime > System.currentTimeMillis()) {
                if (mDevice.findObject(UiSelector().resourceId(
                        "com.android.settings:id/switch_widget")).isChecked == enable)
                    break
            }
        }
        Assert.assertEquals(mDevice.findObject(UiSelector().resourceId(
            "com.android.settings:id/switch_widget")).isChecked, enable)
    }

    fun takeScreenShot(name: String) {
        val device =
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.takeScreenshot(File("/sdcard/DCIM/$name.png"))
    }

    fun launchApp(packageName: String, url: String = "empty") {
        val timeout: Long = 20000
        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            if (url != "empty") {
                intent.setData(Uri.parse(url))
            }
        }
        context.startActivity(intent)
        mDevice.wait(
            Until.hasObject(
                By.pkg(packageName)
                    .depth(0)
            ), timeout
        )
    }
}