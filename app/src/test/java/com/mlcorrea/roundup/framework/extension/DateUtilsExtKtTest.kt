package com.mlcorrea.roundup.framework.extension

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mlcorrea.roundup.AndroidTest
import org.junit.Before
import org.junit.Test


/**
 * Created by manuel on 18/08/19
 */
class DateUtilsExtKtTest : AndroidTest() {

    @Before
    fun setUp() {
    }

    @Test
    fun parseToDateSting() {
    }

    @Test(expected = java.lang.Exception::class)
    @Throws(Exception::class)
    fun `get time when the date is invalid`() {
        //When
        "date".getTime()
    }

    @Test
    fun `get time when the date is valid`() {
        //When
        val time = "2019-08-17T11:52:52.146Z".getTime(FORMAT_TIME_ZONE)
        //then
        Truth.assertThat(time).isEqualTo(1566039172146)
    }

    @Test
    fun `get time in milliseconds`() {
        //When
        val time = "2019-08-17T11:52:52.146Z".getTime(FORMAT_TIME_ZONE)
        val dateString = time.parseToDateSting()
        //then
        assertThat(time).isEqualTo(1566039172146)
        assertThat(dateString).isEqualTo("17-08-2019")
    }

    @Test
    fun `set days to a given date`() {
        //Given
        val time = "2019-08-17T11:52:52.146Z".getTime(FORMAT_TIME_ZONE)
        //When
        val oneWeek = time.setDateDays(6)
        val parseToString = oneWeek.parseToDateSting()
        //Then
        assertThat(oneWeek).isEqualTo(1566557572146)
        assertThat(parseToString).isEqualTo("23-08-2019")
    }
}