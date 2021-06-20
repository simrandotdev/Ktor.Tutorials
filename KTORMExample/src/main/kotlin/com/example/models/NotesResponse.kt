package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class NotesResponse<T>(
    val data: T,
    val success: Boolean
)
