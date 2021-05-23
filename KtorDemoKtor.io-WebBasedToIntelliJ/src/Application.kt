package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port=8080, host="0.0.0.0") {
        install(Routing) {
            homeRoute()
        }
    }.start()
}

fun Routing.homeRoute() {
    get("/") {
        call.respond("Hello Ktor.")
    }
}