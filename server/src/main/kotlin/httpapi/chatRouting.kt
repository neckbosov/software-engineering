package httpapi

import error.InvalidBodyError
import error.InvalidQueryParameterError
import error.NotFoundError
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractChatAPI
import models.chat.Chat
import models.chat.Message

fun Route.configureChatRouting(backend: AbstractChatAPI) {
    route("/v0/chats") {
        get("/chat") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            val result = try {
                backend.getChatById(id)
            } catch (t: Throwable) {
                throw NotFoundError("No chat found with id $id")
            }

            // todo check that user has access to the chat

            call.respond(HttpStatusCode.OK, result)
        }

        get("/msg") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            val result = try {
                backend.getMessageById(id)
            } catch (t: Throwable) {
                throw NotFoundError("No message found with id $id")
            }

            // todo check that user has access to the message

            call.respond(HttpStatusCode.OK, result)
        }

        post("/chat") {
            val chat = try {
                call.receive<Chat>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            // todo validate chat creator(check that request sender is one of the users)
            val result = backend.addChat(chat.user1, chat.user2)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/msg") {
            val msg = try {
                call.receive<Message>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            // todo validate message sender from session(check that msg.senderId is equals to request sender id)
            val result = backend.addMessage(msg.chatId, msg.senderId, msg.content)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/messages") {
            val chatId = call.request.queryParameters["chatId"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `chatId`")

            val startPos = call.request.queryParameters["startPos"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `startPos`")

            val endPos = call.request.queryParameters["endPos"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `endPos`")

            // todo check that request sender has access to the chat

            val result = try {
                backend.getMessages(chatId, startPos, endPos) // todo а могут быть pos невалидны???
            } catch (t: Throwable){
                throw NotFoundError("No chat found with id $chatId")
            }

            call.respond(HttpStatusCode.OK, result)
        }

        get("/user_chats") {
            val userId = call.request.queryParameters["profileID"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `profileID`")

            // todo check that request sender id is equals to userId

            val result = try {
                backend.getChatsByUserId(userId)
            } catch (t: Throwable){
                throw NotFoundError("No user found with id $userId")
            }

            call.respond(HttpStatusCode.OK, result)
        }
    }
}