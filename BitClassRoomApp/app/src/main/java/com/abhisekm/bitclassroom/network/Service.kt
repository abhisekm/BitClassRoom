package com.abhisekm.bitclassroom.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * A retrofit service to fetch a Bitclass lessons
 */
interface BitClassService{
    @GET("api/lesson")
    suspend fun getLessons() : List<NetworkLesson>

}


/**
 * Build the Moshi object that Retrofit will be using along with Kotlin adapter
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Main entry point for network access.
 */
object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://5f3fdac644212d0016fed4f7.mockapi.io/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service : BitClassService by lazy {  retrofit.create(BitClassService::class.java) }
}
