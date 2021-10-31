package httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractChatBackend
import models.chat.Chat
import models.chat.Message

fun Route.configureProfileRouting(backend: AbstractChatBackend) {
    route("/v0/chats") {
        get("/chat") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getChatById(id)

            // todo check that user has access to the chat

            call.respond(HttpStatusCode.OK, result)
        }

        get("/msg") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getMessageById(id)

            // todo check that user has access to the message

            call.respond(HttpStatusCode.OK, result)
        }

        post("/chat") {
            val chat = call.receive<Chat>()

            // todo validate chat creator(check that request sender is one of the users)
            val result = backend.addChat(chat.user1, chat.user2)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/msg") {
            val msg = call.receive<Message>()

            // todo validate message sender from session(check that msg.senderId is equals to request sender id)
            val result = backend.addMessage(msg.chatId, msg.senderId, msg.content)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/messages") {
            val chatId = call.request.queryParameters["chatId"]?.toLongOrNull();
            if (chatId == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid `chatId` value")
                return@get
            }

            val startPos = call.request.queryParameters["startPos"]?.toLongOrNull()
            if (startPos == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid `startPos` value")
                return@get
            }

            val endPos = call.request.queryParameters["endPos"]?.toLongOrNull()
            if (endPos == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid `endPos` value")
                return@get
            }

            // todo check that request sender has access to the chat

            // todo handle exception
            val result = backend.getMessages(chatId, startPos, endPos)

            call.respond(HttpStatusCode.OK, result)
        }

        get("/user_chats") {
            val userId = call.request.queryParameters["profileId"]?.toLongOrNull()
            if (userId == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid `profileId` value")
                return@get
            }

            // todo check that request sender id is equals to userId

            // todo handle exception
            val result = backend.getChatsByUserId(userId)

            call.respond(HttpStatusCode.OK, result)
        }
    }
}