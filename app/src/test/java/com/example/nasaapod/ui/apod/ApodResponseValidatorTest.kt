package com.example.nasaapod.ui.apod

import com.example.nasaapod.api.apod.ApodResponse
import org.junit.Assert.*
import org.junit.Test

class ApodResponseValidatorTest {
    //URL
    @Test
    fun nasaApodUrlValidator_CorrectUrl_ReturnTrue() {
        assertTrue(ApodResponseValidator.isValidApodUrl("image.jpg"))
    }

    @Test
    fun nasaApodUrlValidator_InvalidUrl_ReturnFalse() {
        assertFalse(ApodResponseValidator.isValidApodUrl("image"))
    }

    @Test
    fun nasaPodUrlValidator_InvalidUrlExtension_ReturnFalse() {
        assertFalse(ApodResponseValidator.isValidApodUrl("image.mp4"))
    }

    @Test
    fun nasaApodUrlValidator_InvalidUrlExtensionSequence_ReturnFalse() {
        assertFalse(ApodResponseValidator.isValidApodUrl(".jpg.image"))
    }

    @Test
    fun nasaApodUrlValidator_NullUrl_ReturnFalse() {
        assertNotNull(ApodResponseValidator.isValidApodUrl(null))
    }

    //это видим о будут моки
    val mockResponseToday = ApodResponse(
        "today date",
        "exlpanation today",
        "today_picture_hdurl",
        "jpg",
        "01",
        "title today",
        "today.jpg"
    )

    val mockResponseDayAgo = ApodResponse(
        "day ago date",
        "exlpanation day ago",
        "day_ago_picture_hdurl",
        "jpg",
        "02",
        "title day ago",
        "day_ago.jpg"
    )

    val mockResponseTodayDublicate = ApodResponse(
        "today date",
        "exlpanation today",
        "today_picture_hdurl",
        "jpg",
        "01",
        "title today",
        "today.jpg"
    )


    @Test
    fun nasaApodResponseValidator_SimpleTrue(){
        assertNotEquals(mockResponseToday,mockResponseDayAgo)
    }

    @Test
    fun nasaApodResponseValidator_SimpleFalse(){
        assertEquals(mockResponseToday,mockResponseDayAgo)
    }

    @Test
    fun nasaApodResponseValidator_EqualsTrue(){
        assertEquals(mockResponseToday,mockResponseTodayDublicate)
    }

}