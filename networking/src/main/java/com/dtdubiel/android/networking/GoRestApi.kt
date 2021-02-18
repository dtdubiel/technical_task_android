package com.dtdubiel.android.networking

import com.dtdubiel.android.networking.model.UserRequest
import com.dtdubiel.android.networking.model.UsersResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface GoRestApi {

    @GET("users")
    fun getUsers(@Query(value = "page") page: Long?) : Flowable<UsersResponse>

    @DELETE("users/{id}")
    fun removeUser(@Path(value = "id") id: Int) : Completable

    @POST("users")
    fun addUser(@Body request: UserRequest) : Completable

}
