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
            call.response.headers.append("UUID", UUID.randomUUID().toString())
            call.response.headers.append("Request-Number", Random().nextInt(1000000).toString())
            call.respondText("Request successful")
        }

        get("/files") {
            val file = File("files/americano.jpg")
            // Content-Disposition header tells whether the content
            // will be "inline" (Display in the browser itself.)
            // will be "attachment" (Gets downloaded and saved to your local machine)
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName, "americano.png"
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