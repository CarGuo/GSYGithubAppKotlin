package com.shuyu.github.kotlin.service

import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface UserService {

    @GET("user")
    fun getPersonInfo(
            @Header("forceNetWork") forceNetWork: Boolean
    ): Observable<Response<User>>

    @GET("users/{user}")
    fun getUser(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String
    ): Observable<Response<User>>


    @GET("user/following/{user}")
    fun checkFollowing(
            @Path("user") user: String
    ): Observable<Response<ResponseBody>>

    /**
     * Check if one user follows another
     */
    @GET("users/{user}/following/{targetUser}")
    fun checkFollowing(
            @Path("user") user: String,
            @Path("targetUser") targetUser: String
    ): Observable<Response<ResponseBody>>

    @PUT("user/following/{user}")
    fun followUser(
            @Path("user") user: String
    ): Observable<Response<ResponseBody>>

    @DELETE("user/following/{user}")
    fun unfollowUser(
            @Path("user") user: String
    ): Observable<Response<ResponseBody>>

    @GET("users/{user}/followers")
    fun getFollowers(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<User>>>

    @GET("users/{user}/following")
    fun getFollowing(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<User>>>

    /**
     * List events performed by a user
     */
    @GET("users/{user}/events")
    fun getUserEvents(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>

    /**
     * List events that a user has received
     */
    @GET("users/{user}/received_events")
    fun getNewsEvent(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>

    @GET("orgs/{org}/members")
    fun getOrgMembers(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("org") org: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<User>>>

    @GET("users/{user}/orgs")
    fun getUserOrgs(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String
    ): Observable<Response<ArrayList<User>>>


}
