package com.mlcorrea.roundup

import io.mockk.MockKAnnotations
import org.junit.rules.TestRule

/**
 * Created by manuel on 18/08/19
 */
class InjectMocksRule {

    companion object {
        fun create(testClass: Any) = TestRule { statement, _ ->
            MockKAnnotations.init(testClass)
            statement
        }
    }
}
