package id.ac.umn.storyapplication.api

import id.ac.umn.storyapplication.model.DefaultResponse
import id.ac.umn.storyapplication.model.LoginResponse
import id.ac.umn.storyapplication.model.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<LoginResponse>



    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token:String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<StoryResponse>

    @GET("stories")
    fun getStory(@Header("Authorization") token:String): Call<StoryResponse>

    @GET("stories")
    suspend fun getPagingStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories?location=1")
    fun getStoryMap(
        @Header("Authorization") token:String,
        @Query("size") size: Int
    ): Call<StoryResponse>
}