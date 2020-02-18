package com.choo.choobackend

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import java.util.UUID


class EventSerdeTest {
    @Test
    fun `should throw meaningful exception if type is invalid`() {
        //language=JSON
        val json = "{\"type\": \"CreateLobbiezzz\",\"clientId\": \"00000000-0000-0000-0000-000000000000\"}"

        assertThatThrownBy { EventSerde().deserialize(json) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessageContaining("Can't find EventType for event")

    }

    @Test
    fun `should deserialize command`() {
        //language=JSON
        val json = "{\"type\": \"CreateLobby\",\"clientId\": \"00000000-0000-0000-0000-000000000000\"}"

        val result = EventSerde().deserialize(json)

        assertThat(result).isEqualTo(CreateLobby.new(ClientId(UUID(0,0))))
//        assertThat(result).isEqualTo(CreateLobby.new(UUID(0,0)))
    }
}
