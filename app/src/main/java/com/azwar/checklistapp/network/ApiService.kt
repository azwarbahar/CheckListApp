package com.azwar.checklistapp.network

import com.azwar.checklistapp.Constants
import com.azwar.checklistapp.data.Responses
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //Get Auth API
    //Request Token
    @GET(Constants.BASE_URL + "login")
    fun login(
        @Field("password") password: String?,
        @Field("username") username: String?,
    ): Call<Responses.AuthResponse>

    //Register
    @GET(Constants.BASE_URL + "register")
    fun register(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("username") username: String?,
    ): Call<Responses.AuthResponse>


    //CHECKLIST API
    //Get All Checklist
    @GET(Constants.BASE_URL + "/checklist")
    fun getAllChacklist(
        @Header("Authorization") token: String
    ): Call<Responses.GetChecklistResponse>

    // Create new checklist
    @FormUrlEncoded
    @POST(Constants.BASE_URL + "checklist")
    fun createChacklist(
        @Header("Authorization") token: String,
        @Field("name") name: String?,
    ): Call<Responses.SaveChecklistResponse>

    // Delete checklist
    @DELETE(Constants.BASE_URL + "checklist/{checklistId}")
    fun deleteChacklist(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
    ): Call<Responses.DeleteChecklistResponse>


    //CHECKLIST ITEM API
    //Get All Checklist Item
    @GET(Constants.BASE_URL + "checklist/{checklistId}/item")
    fun getAllChacklistItem(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
    ): Call<Responses.GetChecklistItemResponse>

    // Create new checklist item
    @FormUrlEncoded
    @POST(Constants.BASE_URL + "checklist/{checklistId}/item")
    fun createChacklistItem(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
        @Field("itemName") itemName: String?,
    ): Call<Responses.SaveChecklistItemResponse>

    //Get checklist item in Checklist by checklist id
    @GET(Constants.BASE_URL + "checklist/{checklistId}/item/{checklistItemId}")
    fun getChacklistItemById(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
    ): Call<Responses.GetChecklistItemResponse>

    // Update item status
    @PUT(Constants.BASE_URL + "checklist/{checklistId}/item/{checklistItemId}")
    fun updateItemStatus(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
        @Path("checklistItemId") checklistItemId: Int?
    ): Call<Responses.UpdateChecklistItemResponse>

    // Delete checklist Item
    @DELETE(Constants.BASE_URL + "checklist/{checklistId}/item/{checklistItemId}")
    fun deleteChacklistItem(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
        @Path("checklistItemId") checklistItemId: Int?
    ): Call<Responses.DeleteChecklistItemResponse>

    // Rename item by checklist item id
    @PUT(Constants.BASE_URL + "checklist/{checklistId}/item/{checklistItemId}")
    fun renameItemchecklistItem(
        @Header("Authorization") token: String,
        @Path("checklistId") checklistId: Int?,
        @Path("checklistItemId") checklistItemId: Int?,
        @Field("itemName") itemName: String?
    ): Call<Responses.UpdateChecklistItemResponse>

}