package httpapi

import backend.api.authaccess.AuthorizedChatAPI
import error.InvalidBodyError
import error.InvalidQueryParameterError
import error.NotFoundError
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.auth.SimpleJwt
import models.chat.Chat
import models.chat.Message

fun Route.configureChatRouting(backend: AuthorizedChatAPI, jwt: SimpleJwt) {
    route("/v0/chats") {
        get("/chat") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            authorized(jwt) { agent ->
                val result = try {
                    backend.getChatById(agent.id, id)
                } catch (t: Throwable) {
                    throw NotFoundError("No chat found with id $id")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/msg") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            authorized(jwt) { agent ->
                val result = try {
                    backend.getMessageById(agent.id, id)
                } catch (t: Throwable) {
                    throw NotFoundError("No message found with id $id")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        post("/chat") {
            authorized(jwt) { agent ->
                val chat = try {
                    call.receive<Chat>()
                } catch (t: Throwable) {
                    throw InvalidBodyError("Invalid body")
                }
                val result = backend.addChat(agent.id, chat.user1, chat.user2)
                call.respond(HttpStatusCode.OK, result)
            }
        }

        post("/msg") {
            authorized(jwt) { agent ->
                val msg = try {
                    call.receive<Message>()
                } catch (t: Throwable) {
                    throw InvalidBodyError("Invalid body")
                }
                if (agent.id != msg.senderId) {
                    throw InvalidBodyError("your id doesn't match sender_id")
                }
                val result = backend.addMessage(msg.senderId, msg.chatId, msg.content)
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/messages") {
            val chatId = call.request.queryParameters["chatId"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `chatId`")

            val startPos = call.request.queryParameters["startPos"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `startPos`")

            val endPos = call.request.queryParameters["endPos"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `endPos`")

            authorized(jwt) { agent ->
                val result = try {
                    backend.getMessages(agent.id, chatId, startPos, endPos) // todo а могут быть pos невалидны???
                } catch (t: Throwable) {
                    throw NotFoundError("No chat found with id $chatId")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/user_chats") {
            val userId = call.request.queryParameters["profileId"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `profileID`")

            // todo check that request sender id is equals to userId

            authorized(jwt) { agent ->
                println(agent.id)
                val result = try {
                    backend.getChatsByUserId(agent.id, userId)
                } catch (t: Throwable) {
                    throw NotFoundError("No user found with id $userId")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}