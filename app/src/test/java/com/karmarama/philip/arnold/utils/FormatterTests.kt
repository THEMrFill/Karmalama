package com.karmarama.philip.arnold.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test

class FormatterTests {
    @Test
    fun numbFormatterTestZero() {
        assertEquals(Formatter.NumberFormatter(0.0), "0")
    }
    @Test
    fun numbFormatterTestHalf() {
        assertEquals(Formatter.NumberFormatter(0.5), "0.5")
    }
    @Test
    fun numbFormatterTestTwo() {
        assertEquals(Formatter.NumberFormatter(2.0), "2")
    }
    @Test
    fun numbFormatterTestTwoAndAHalf() {
        assertEquals(Formatter.NumberFormatter(2.5), "2.5")
    }


    @Test
    fun dateFormatterTestKnown1() {
        assertEquals(Formatter.DayFormatter("2019-07-02 00:00:00"), "TUE")
    }
    @Test
    fun dateFormatterTestKnown2() {
        assertEquals(Formatter.DayFormatter("2019-06-01 12:00:00"), "SAT")
    }
    @Test
    fun dateFormatterTestKnownLong() {
        assertEquals(Formatter.DayFormatter("2019-07-02 00:00:00", false), "Tuesday")
    }
}