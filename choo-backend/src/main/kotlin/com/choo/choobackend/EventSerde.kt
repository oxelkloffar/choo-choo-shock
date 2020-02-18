package com.choo.choobackend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class EventSerde {
    private val mapper = jacksonObjectMapper()

    fun serialize(event: Any): String {
        return mapper.writeValueAsString(event)
    }

    fun deserialize(event: String): Command {
        val clazz = event.type()
        val jclazz = clazz.java

        return mapper.readValue(event, jclazz)
//        return when (event.type()) {
//            CommandType.CREATE_LOBBY -> mapper.readValue<CreateLobby>(event)
//            CommandType.JOIN_LOBBY -> mapper.readValue<JoinLobby>(event)
//        }
    }

    private fun String.type(): KClass<out Command> {
        Command::class.sealedSubclasses.forEach { clazz ->
            val name = clazz.simpleName?:throw Exception("thi")
            if (name in this) {
                return clazz
            }
        }
        throw IllegalStateException("Can't find EventType for event $this")
    }
}
