package com.example.routes.notes

import com.example.Database
import com.example.entities.NotesEntity
import com.example.models.CreateNoteRequest
import com.example.models.Note
import com.example.models.NotesResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.ktorm.dsl.*

fun Application.notesRoutes() {
    routing {
        post("/notes") {
            val request = call.receive<CreateNoteRequest>()
            val result = Database.database.insert(NotesEntity) {
                set(it.note, request.note)
            }
            if (result == 1) {
                call.respond(
                    HttpStatusCode.OK,
                    NotesResponse(
                        success = true,
                        data = "Value successfully inserted"
                    ))
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    NotesResponse(
                        success = false,
                        data = "Failed to insert."
                    ))
            }
        }

        get("/notes") {
            val notes = Database.database.from(NotesEntity)
                .select().map {
                    val id = it[NotesEntity.id]
                    val note = it[NotesEntity.note]
                    Note(id = id ?: -1, note = note ?: "")
                }
            call.respond(HttpStatusCode.OK, notes)
        }

        get("/notes/{id}") {
            val id = call.parameters["id"]?.toInt() ?: -1

            val note = Database.database.from(NotesEntity)
                .select()
                .where {
                    NotesEntity.id eq id
                }
                .mapNotNull {
                    val id = it[NotesEntity.id]
                    val note = it[NotesEntity.note]
                    Note(id = id!!, note = note!!)
                }.firstOrNull()
            if(note != null) {
                call.respond(HttpStatusCode.OK, NotesResponse<Note>(
                    success = true,
                    data = note
                ))
            } else {
                call.respond(
                    HttpStatusCode.NotFound, NotesResponse<String>(
                        success = false,
                        data = "Note not found"
                    )
                )
            }
        }

        delete("/notes/{id}") {
            val id = call.parameters["id"]?.toInt() ?: -1
            val rowsEffected = Database.database.delete(NotesEntity) { it.id eq id}
            if (rowsEffected == 1) {
                call.respond(
                    HttpStatusCode.NoContent,
                    NotesResponse<String>(success = true, data = "Note successfully deleted")
                )
                return@delete
            }
            call.respond(
                HttpStatusCode.BadRequest,
                NotesResponse<String>(success = false, data = "Failed to delete note.")
            )
        }

        put("/notes/{id}") {
            val id = call.parameters["id"]?.toInt() ?: -1
            val updatedNote = call.receive<CreateNoteRequest>()

            val rowsEffected = Database.database
                .update(NotesEntity) {
                    set(it.note, updatedNote.note)
                    where {
                        it.id eq id
                    }
                }
            if (rowsEffected == 1) {
                call.respond(
                    HttpStatusCode.NoContent,
                    NotesResponse<String>(success = true, data = "Note successfully updated")
                )
                return@put
            }
            call.respond(
                HttpStatusCode.BadRequest,
                NotesResponse<String>(success = false, data = "Failed to update note.")
            )
        }
    }
}