package com.shuyu.github.kotlin.service

import com.shuyu.github.kotlin.model.Issue
import com.shuyu.github.kotlin.model.Repository
import com.shuyu.github.kotlin.model.SearchResult
import com.shuyu.github.kotlin.model.User

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface SearchService {

    @GET("search/users")
    fun searchUsers(
            @Query(value = "q", encoded = true) query: String,
            @Query("sort") sort: String,
            @Query("order") order: String,
            @Query("page") page: Int
    ): Observable<Response<SearchResult<User>>>

    @GET("search/repositories")
    fun searchRepos(
            @Query(value = "q", encoded = true) query: String,
            @Query("sort") sort: String,
            @Query("order") order: String,
            @Query("page") page: Int
    ): Observable<Response<SearchResult<Repository>>>

    @GET("search/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun searchIssues(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Query(value = "q", encoded = true) query: String,
            @Query("sort") sort: String,
            @Query("order") order: String,
            @Query("page") page: Int
    ): Observable<Response<SearchResult<Issue>>>

}
