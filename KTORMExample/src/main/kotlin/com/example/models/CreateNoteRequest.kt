package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateNoteRequest(
    val note: String
)
