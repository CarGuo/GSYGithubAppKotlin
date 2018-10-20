package com.shuyu.github.kotlin.service

import com.shuyu.github.kotlin.model.bean.CommentRequestModel
import com.shuyu.github.kotlin.model.bean.Issue
import com.shuyu.github.kotlin.model.bean.IssueEvent

import java.util.ArrayList

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface IssueService {

    @GET("repos/{owner}/{repo}/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun getRepoIssues(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("state") state: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<Issue>>>

    @GET("user/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun getUserIssues(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Query("filter") filter: String,
            @Query("state") state: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<Issue>>>

    @GET("repos/{owner}/{repo}/issues/{issueNumber}")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun getIssueInfo(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int
    ): Observable<Response<Issue>>

    @GET("repos/{owner}/{repo}/issues/{issueNumber}/timeline")
    @Headers("Accept: application/vnd.github.mockingbird-preview")
    fun getIssueTimeline(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<IssueEvent>>>

    @GET("repos/{owner}/{repo}/issues/{issueNumber}/comments")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun getIssueComments(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<IssueEvent>>>

    @GET("repos/{owner}/{repo}/issues/{issueNumber}/events")
    @Headers("Accept: application/vnd.github.html")
    fun getIssueEvents(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int,
            @Query("page") page: Int
    ): Observable<Response<ArrayList<IssueEvent>>>

    @POST("repos/{owner}/{repo}/issues/{issueNumber}/comments")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun addComment(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int,
            @Body body: CommentRequestModel
    ): Observable<Response<IssueEvent>>

    @PATCH("repos/{owner}/{repo}/issues/comments/{commentId}")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun editComment(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("commentId") commentId: String,
            @Body body: CommentRequestModel
    ): Observable<Response<IssueEvent>>

    @DELETE("repos/{owner}/{repo}/issues/comments/{commentId}")
    fun deleteComment(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("commentId") commentId: String
    ): Observable<Response<ResponseBody>>

    @PATCH("repos/{owner}/{repo}/issues/{issueNumber}")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun editIssue(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int,
            @Body body: Issue
    ): Observable<Response<Issue>>

    @POST("repos/{owner}/{repo}/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun createIssue(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Body body: Issue
    ): Observable<Response<Issue>>

}
