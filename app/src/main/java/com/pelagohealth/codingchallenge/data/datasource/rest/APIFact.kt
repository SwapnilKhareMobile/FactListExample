package com.pelagohealth.codingchallenge.data.datasource.rest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class for Fact API response
 */
@JsonClass(generateAdapter = true)
data class APIFact(
    val id: String,
    val text: String,
    @Json(name = "source_url")
    val sourceUrl: String
)