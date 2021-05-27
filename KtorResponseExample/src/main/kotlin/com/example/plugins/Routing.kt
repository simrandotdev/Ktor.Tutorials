package com.example.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {

    routing {
        get("/") {
            val responseObject = UserResponse("John", "john@gmail.com")
            call.respond(responseObject)
        }
    }
}

@Serializable
data class UserResponse(
    val name: String,
    val email: String
)