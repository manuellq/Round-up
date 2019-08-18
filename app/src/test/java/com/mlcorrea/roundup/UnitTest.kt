package com.mlcorrea.roundup

import org.junit.Rule

/**
 * Created by manuel on 18/08/19
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */
abstract class UnitTest {

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}
