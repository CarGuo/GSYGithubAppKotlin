package com.shuyu.github.kotlin.service

import com.shuyu.github.kotlin.model.bean.Branch
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.FileModel
import com.shuyu.github.kotlin.model.bean.Release
import com.shuyu.github.kotlin.model.bean.Repository
import com.shuyu.github.kotlin.model.bean.User

import java.util.ArrayList

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface RepoService {

    @GET("users/{user}/starred")
    fun getStarredRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("sort") sort: String,
            @Query("direction") direction: String
    ): Observable<Response<ArrayList<Repository>>>

    @GET("user/repos")
    fun getUserRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Query("page") page: Int,
            @Query("type") type: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String
    ): Observable<Response<ArrayList<Repository>>>

    /**
     * List user repositories
     */
    @GET("users/{user}/repos")
    fun getUserPublicRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("type") type: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String
    ): Observable<retrofit2.Response<ArrayList<Repository>>>

    /**
     * Check if you are starring a repository
     */
    @GET("user/starred/{owner}/{repo}")
    fun checkRepoStarred(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ResponseBody>>

    /**
     * Star a repository
     */
    @PUT("user/starred/{owner}/{repo}")
    fun starRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ResponseBody>>

    /**
     * Unstar a repository
     */
    @DELETE("user/starred/{owner}/{repo}")
    fun unstarRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ResponseBody>>

    @GET("user/subscriptions/{owner}/{repo}")
    fun checkRepoWatched(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ResponseBody>>

    @PUT("user/subscriptions/{owner}/{repo}")
    fun watchRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ResponseBody>>

    @DELETE("user/subscriptions/{owner}/{repo}")
    fun unwatchRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ResponseBody>>

    @GET
    @Headers("Accept: application/vnd.github.html")
    fun getFileAsHtmlStream(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Url url: String
    ): Observable<Response<ResponseBody>>

    @GET
    @Headers("Accept: application/vnd.github.VERSION.raw")
    fun getFileAsStream(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Url url: String
    ): Observable<Response<ResponseBody>>

    @GET("repos/{owner}/{repo}/contents/{path}")
    fun getRepoFiles(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path(value = "path", encoded = true) path: String,
            @Query("ref") branch: String
    ): Observable<Response<ArrayList<FileModel>>>

    @GET("repos/{owner}/{repo}/branches")
    fun getBranches(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ArrayList<Branch>>>

    @GET("repos/{owner}/{repo}/tags")
    fun getTags(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<ArrayList<Branch>>>

    @GET("repos/{owner}/{repo}/stargazers")
    fun getStargazers(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path(value = "owner") owner: String,
            @Path(value = "repo") repo: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<User>>>

    @GET("repos/{owner}/{repo}/subscribers")
    fun getWatchers(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<User>>>

    @GET("repos/{owner}/{repo}")
    fun getRepoInfo(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<Repository>>

    @POST("repos/{owner}/{repo}/forks")
    fun createFork(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<Response<Repository>>

    @GET("repos/{owner}/{repo}/forks")
    fun getForks(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<Repository>>>

    /**
     * List public events for a network of repositories
     */
    @GET("networks/{owner}/{repo}/events")
    fun getRepoEvent(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<Event>>>

    @GET("repos/{owner}/{repo}/releases")
    @Headers("Accept: application/vnd.github.html")
    fun getReleases(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<Release>>>

    @GET("repos/{owner}/{repo}/releases/tags/{tag}")
    @Headers("Accept: application/vnd.github.html")
    fun getReleaseByTagName(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("tag") tag: String
    ): Observable<Response<Release>>

}
