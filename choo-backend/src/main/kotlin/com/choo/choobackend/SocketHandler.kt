package com.choo.choobackend

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

data class Lobby(
        val clients: List<Client>,
        val code: LobbyCode
) {
    fun addClient(client: Client) = copy(clients = clients.plus(client))
}

data class Client(
        val id: ClientId,
        val socket: WebSocketSession
)

@Component
class SocketHandler(private val eventSerde: EventSerde) : TextWebSocketHandler() {
//    private val sessions: MutableList<WebSocketSession> = mutableListOf()

    private val lobbies = ConcurrentHashMap<LobbyCode, Lobby>()

    init {
        println("I WAS LOADED!")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

        val command = eventSerde.deserialize(message.payload)
        logger.info { "Received command $command" }

        when (command) {
            is CreateLobby -> {
                val client = Client(command.clientId, session)
                lobbies[LobbyCode("ABCD")] = Lobby(listOf(client), LobbyCode("ABCD"))
            }
            is JoinLobby -> {
                val client = Client(command.clientId, session)
                lobbies.computeIfPresent(command.lobbyCode) { _, lobby ->
                    lobby.addClient(client)
                } ?: throw IllegalStateException("Staten är olaglig (invalid join code)")
            }
        }
// webSocketSession.sendMessage(message)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        // todo remove client from lobby
    }

//    override fun afterConnectionEstablished(session: WebSocketSession) {
//        println("connection established")
//        sessions.add(session)
//    }
}
