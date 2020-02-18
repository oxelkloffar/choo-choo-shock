package com.choo.choobackend

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue
import java.util.UUID

data class ClientId(@get:JsonIgnore val value: UUID) {
    @JsonValue
    override fun toString() = value.toString()
}

data class LobbyId(@JsonValue val value: UUID)
data class LobbyCode(@JsonValue val value: String)

sealed class Command

data class CreateLobby(val type: String, val clientId: ClientId) : Command() {
    companion object {
        fun new(clientId: ClientId) = CreateLobby(CreateLobby::class.java.simpleName, clientId)
    }

    data class Success(val lobbyId: LobbyId)
    data class Failure(val msg: String)
}

data class JoinLobby(val type: String, val clientId: ClientId, val lobbyCode: LobbyCode) : Command() {
    data class Success(val lobbyId: LobbyId)
    data class Failure(val msg: String)
}
