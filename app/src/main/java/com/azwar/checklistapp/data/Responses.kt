package com.azwar.checklistapp.data

class Responses {

    data class AuthResponse(
        val `data`: TokenModel,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class GetChecklistResponse(
        val `data`: List<KendaraanModel>,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class SaveChecklistResponse(
        val `data`: KendaraanModel,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class DeleteChecklistResponse(
        val `data`: KendaraanModel,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class GetChecklistItemResponse(
        val `data`: List<KendaraanItemModel>,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class SaveChecklistItemResponse(
        val `data`: KendaraanItemModel,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class UpdateChecklistItemResponse(
        val `data`: KendaraanItemModel,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )

    data class DeleteChecklistItemResponse(
        val `data`: KendaraanItemModel,
        val statusCode: Int,
        val message: String,
        val errorMessage: String
    )
}