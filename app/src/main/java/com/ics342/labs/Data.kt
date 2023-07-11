package com.ics342.labs

import com.squareup.moshi.Json

data class MyData(
    val id: Int,
    @Json(name = "give_name") val givenName: String,
    @Json(name = "family_name") val familyName: String,
    val age: Int
)
