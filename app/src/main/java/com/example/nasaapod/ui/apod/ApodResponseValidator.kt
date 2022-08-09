package com.example.nasaapod.ui.apod

import com.example.nasaapod.api.apod.ApodResponse
import java.util.regex.Pattern

class ApodResponseValidator() {

    companion object {
        /*
        если не ошибаюсь патерн такой что в строке слева может быть что угодно кроме пробелов
        но обязатеьлно должно заканчиваться на .jpg
        вроде через символ | можно и другие форматы определить но не уверен
        */
        private val URL_PATTERN = Pattern.compile(
            "\\S+(?:jpg)$"
        )

        fun isValidApodUrl(url: CharSequence?): Boolean {
            return url != null && URL_PATTERN.matcher(url).matches()
        }

        fun isValidResponse(response: ApodResponse): Boolean {
            return response != null
        }

        fun isResponseAreNotEquals(
            responseToday: ApodResponse,
            responseDayAgo: ApodResponse
        ): Boolean {
            return responseToday.equals(responseDayAgo)
        }

    }

}