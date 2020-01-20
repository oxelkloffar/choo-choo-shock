package com.choo.choobackend

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


@Component
class SocketHandler : TextWebSocketHandler() {
    var sessions: MutableList<WebSocketSession> = mutableListOf()

    init {
        println("I WAS LOADED!")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        println("handleTextMessage")
        println(message)
        for (webSocketSession in mutableListOf<WebSocketSession>().apply { addAll(sessions) }) {
//            val value: Map<*, *> = Gson().fromJson(message.getPayload(), MutableMap::class.java)
            try {
                webSocketSession.sendMessage(message)
            } catch (e: Exception) {
                println("exception when sending to socket")
                sessions.remove(session)
            }
//            webSocketSession.sendMessage(TextMessage("Hello " + value["name"] + " !"))
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("connection established")
        sessions.add(session)
    }
}
