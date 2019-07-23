package com.shuyu.github.kotlin.service

import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.model.bean.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface RepoService {

    @GET("users/{user}/repos")
    fun getUserRepository100StatusDao(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("sort") sort: String = "pushed",
            @Query("per_page") per_page: Int = 100
    ): Observable<Response<ArrayList<Repository>>>



    @GET("users/{user}/starred")
    fun getStarredRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("sort") sort: String = "updated",
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Repository>>>

    @GET("user/repos")
    fun getUserRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Query("page") page: Int,
            @Query("type") type: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Repository>>>

    /**
     * List user repositories
     */
    @GET("users/{user}/repos")
    fun getUserPublicRepos(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("sort") sort: String = "pushed",
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
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
            @Path("path", encoded = true) path: String,
            @Query("ref") branch: String = "master"
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
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Repository>>>

    /**
     * List public events for a network of repositories
     */
    @GET("networks/{owner}/{repo}/events")
    fun getRepoEvent(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>

    @GET("repos/{owner}/{repo}/releases")
    @Headers("Accept: application/vnd.github.html")
    fun getReleases(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Release>>>


    @GET("repos/{owner}/{repo}/releases")
    fun getReleasesNotHtml(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Release>>>


    @GET("repos/{owner}/{repo}/releases/tags/{tag}")
    @Headers("Accept: application/vnd.github.html")
    fun getReleaseByTagName(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("tag") tag: String
    ): Observable<Response<Release>>


    @GET("https://github.com/trending/{languageType}")
    @Headers("Content-Type: text/plain;charset=utf-8")
    fun getTrendData(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("languageType") languageType: String,
            @Query("since") since: String): Observable<Response<String>>

    @GET("repos/{owner}/{repo}/readme")
    @Headers("Content-Type: text/plain;charset=utf-8", "Accept: application/vnd.github.html")
    fun getReadmeHtml(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("ref") branch: String = "master"): Observable<Response<String>>


    @GET("repos/{owner}/{repo}/contents/{path}")
    @Headers("Content-Type: text/plain;charset=utf-8", "Accept: application/vnd.github.html")
    fun getRepoFilesDetail(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path(value = "path", encoded = true) path: String,
            @Query("ref") branch: String = "master"
    ): Observable<Response<String>>

}
