package org.polik.charfrequency.web

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class CharFrequencyControllerTest {

    @field:Autowired
    private lateinit var mockMvc: MockMvc
    private val REST_URL = "/api/v1/char-frequency"
    private val mapper = ObjectMapper()

    @Test
    fun passLowerCaseLetters() {
        val request = TextInputRequest("aaaaabcccc")
        perform(MockMvcRequestBuilders.post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(request)))
            .andExpect(status().isOk)
    }

    @Test
    fun passLowerAndUpperCaseLetters() {
        val request = TextInputRequest("AbCdEfGh")
        perform(MockMvcRequestBuilders.post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(request)))
            .andExpect(status().isOk)
    }

    @Test
    fun passEmptyText() {
        val request = TextInputRequest("")
        perform(MockMvcRequestBuilders.post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(request)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun passTextWithSpaces() {
        val request = TextInputRequest("t e s t")
        perform(MockMvcRequestBuilders.post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(request)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun passTextWithDigits() {
        val request = TextInputRequest("te1234dst")
        perform(MockMvcRequestBuilders.post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(request)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun passRussianLetters() {
        val request = TextInputRequest("абвгде")
        perform(MockMvcRequestBuilders.post(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValue(request)))
            .andExpect(status().isBadRequest)
    }

    @Throws(Exception::class)
    private fun perform(builder: MockHttpServletRequestBuilder): ResultActions {
        return mockMvc.perform(builder)
    }

    fun <T> writeValue(obj: T): String {
        return try {
            mapper.writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            throw IllegalStateException("Invalid write to JSON:\n'$obj'", e)
        }
    }
}