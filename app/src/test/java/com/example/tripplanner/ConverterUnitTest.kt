package com.example.tripplanner
import com.example.tripplanner.utils.Converter
import junit.framework.Assert.*
import org.junit.Test

class ConverterUnitTest {

    private val converterObj = Converter

    @Test
    fun test_convert_duration(){
        assertEquals(converterObj.convertDistance(10000f), "10km")
        assertEquals(converterObj.convertDistance(920f), "920m")
        assertEquals(converterObj.convertDistance(1100f), "1km 100m")
    }

    @Test
    fun test_convert_distance(){
        assertEquals(converterObj.convertDuration(7200), "2h")
        assertEquals(converterObj.convertDuration(1000), "16m")
        assertEquals(converterObj.convertDuration(2010), "33m")
        assertEquals(converterObj.convertDuration(2401), "40m")
    }
}

