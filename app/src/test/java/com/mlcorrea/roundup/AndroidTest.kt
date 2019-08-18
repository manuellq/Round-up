package com.mlcorrea.roundup

import android.app.Application
import android.content.Context
import com.mlcorrea.roundup.ui.base.BaseActivity
import io.mockk.mockk
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by manuel on 18/08/19
 *
 * Base class for Android tests. Inherit from it to create test cases which contain android
 * framework dependencies or components.
 *
 * @see UnitTest
 */

@Config(
    //constants = BuildConfig::class,
    application = AndroidTest.ApplicationStub::class,
    sdk = [21]
)
@RunWith(RobolectricTestRunner::class)
abstract class AndroidTest {

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@AndroidTest)

    fun context(): Context = RuntimeEnvironment.application

    fun activityContext(): Context = mockk<BaseActivity>()

    internal class ApplicationStub : Application()
}
