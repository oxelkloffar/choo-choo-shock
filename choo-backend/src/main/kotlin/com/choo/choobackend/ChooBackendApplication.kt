package com.choo.choobackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


@SpringBootApplication
class ChooBackendApplication

fun main(args: Array<String>) {
	runApplication<ChooBackendApplication>(*args)
}

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
	override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
		registry.addHandler(SocketHandler(), "/ws").setAllowedOrigins("*")
	}
}
