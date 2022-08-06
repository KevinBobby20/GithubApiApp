package com.test.kompastest.api

import com.test.kompastest.model.NameModel
import com.test.kompastest.model.RepoModel
import com.test.kompastest.model.SearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    fun getData(
        @Query("q") query : String
    ): Call<SearchModel>

    @GET("users/{name}")
    fun getName(
        @Path("name") name : String
    ): Call<NameModel>

    @GET("users/{name}/repos")
    fun getRepo(
        @Path("name") name : String
    ): Call<List<RepoModel>>
}