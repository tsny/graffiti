package com.example.graffiti

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


object ServiceBuilder {

    private const val baseUrl = "https://api.reddit.com/"
    private val client = OkHttpClient.Builder().build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

    interface RedditService {

        // Example: https://api.reddit.com/r/apexlegends/hot
        @GET("r/{sub}/hot")
        fun listSubmissions(
            @Path("sub") sub: String,
            @Query("after") after: String?
        ): Call<Listing>

        // Example: https://api.reddit.com/r/apexlegends/comments/ojfoqi
        @GET("r/{sub}/comments/{post}")
        fun getPost(
            @Path("sub") sub: String,
            @Path("post") post: String,
            @Query("depth") depth: Int?,
            @Query("limit") limit: Int?
        ): Call<Comments>
    }

}
