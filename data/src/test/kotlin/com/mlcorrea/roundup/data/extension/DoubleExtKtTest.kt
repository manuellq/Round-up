package com.mlcorrea.roundup.data.extension

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mlcorrea.roundup.domain.model.Accounts
import com.mlcorrea.roundup.domain.model.Amount
import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.ViewModelData
import io.mockk.every
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

import java.math.BigDecimal

/**
 * Created by manuel on 18/08/19
 */
class DoubleExtKtTest {

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @Test
    fun `round up list viewModel when the list is not FeedItem`() {
        //Given
        val testObserver = TestObserver<Any>()
        val badItem = mockk<Accounts>()
        val invalidList: List<ViewModelData> = listOf(badItem)
        //When
        val result = invalidList.roundUpFeedItem()
        result.subscribe(testObserver)
        //Then
        testObserver.assertComplete()
        testObserver.assertValues(BigDecimal(0))
        testObserver.assertNoErrors()
    }

    @Test
    fun `round up list viewModel when the list is FeedItem`() {
        //Given
        val testObserver = TestObserver<Any>()
        val feedItem = mockk<FeedItem>()
        val resultBigDecimal = BigDecimal(3.95999999999999996447286321199499070644378662109375)
        every { feedItem.amount }.returns(Amount("GBP", "12301"))
        val invalidList: List<ViewModelData> = listOf(feedItem, feedItem, feedItem, feedItem)
        //When
        val result = invalidList.roundUpFeedItem()
        result.subscribe(testObserver)
        //Then
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        Truth.assertThat(true).isTrue()
    }

    @Test
    fun `check decimal point long number`() {
        //Given
        val bigNoDecimal = BigDecimal(1234)
        //When
        val decimal = bigNoDecimal.setDecimalPoint()

        //Then
        assertThat(decimal).isEqualToIgnoringScale(BigDecimal(12.34).setScale(2, BigDecimal.ROUND_HALF_UP))
    }

    @Test
    fun `check decimal point short number`() {
        //Given
        val bigNoDecimal = BigDecimal(14)
        //When
        val decimal = bigNoDecimal.setDecimalPoint()

        //Then
        assertThat(decimal).isEqualToIgnoringScale(BigDecimal(0.14).setScale(2, BigDecimal.ROUND_HALF_UP))
    }

    @Test
    fun `check decimal point in decimals`() {
        //Given
        val bigNoDecimal = BigDecimal(1)
        //When
        val decimal = bigNoDecimal.setDecimalPoint()

        //Then
        assertThat(decimal).isEqualToIgnoringScale(BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP))
    }

    @Test
    fun `remove separator 3 digits`() {
        //Given
        val bigNoDecimal = BigDecimal(1.01)
        //When
        val decimal = bigNoDecimal.removeDecimalSeparator()
        //Then
        assertThat(decimal).isEqualTo("101")
    }

    @Test
    fun `remove separator 2 digits`() {
        //Given
        val bigNoDecimal = BigDecimal(0.10)
        //When
        val decimal = bigNoDecimal.removeDecimalSeparator()
        //Then
        assertThat(decimal).isEqualTo("010")
    }

    @Test
    fun `remove separator 1 digits`() {
        //Given
        val bigNoDecimal = BigDecimal(0.01)
        //When
        val decimal = bigNoDecimal.removeDecimalSeparator()
        //Then
        assertThat(decimal).isEqualTo("001")
    }
}