package com.example.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlinx.serialization.Serializable
import java.io.File
import java.util.*

fun Application.configureRouting() {

    routing {
        get("/") {
            val responseObject = UserResponse("John", "john@gmail.com")
            call.respond(responseObject)
        }

        get("/headers") {
            call.response.headers.append("server-name", "KtorServer")
            call.response.headers.append("chocolate", "I love it.")
            call.respondText("Headers Attached")
        }

        get("/fileDownload") {
            val file = File("files/americano.jpg")

            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName, "downloadableImage.jpg"
                ).toString()
            )

            call.respondFile(file)
        }

        get("/fileOpen") {
            val file = File("files/expresso.jpg")

            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Inline.withParameter(
                    ContentDisposition.Parameters.FileName, "openImage.jpg"
                ).toString()
            )

            call.respondFile(file)
        }
    }
}

@Serializable
data class UserResponse(
    val name: String,
    val email: String
)