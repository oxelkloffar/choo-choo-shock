package com.choo.choobackend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.UUID


class DataClassesTest {
    @Test
    fun `should serialize to proper json`() {
        val clientId = ClientId(UUID(0, 0))

        val result = jacksonObjectMapper().writeValueAsString(clientId)
        assertThat(result).isEqualTo("\"00000000-0000-0000-0000-000000000000\"")
    }
}
