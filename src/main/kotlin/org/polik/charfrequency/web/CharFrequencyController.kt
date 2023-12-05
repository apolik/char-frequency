package org.polik.charfrequency.web

import jakarta.validation.ValidationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class CharFrequencyController {
    @PostMapping("/char-frequency")
    fun calculateCharacterFrequency(@RequestBody input: TextInputRequest): Map<Char, Long> {
        val text = input.text
        if (!text.matches((Regex("[A-Za-z]+")))) {
            throw ValidationException("Text input must not be blank and must contain only english letters")
        }

        return text.groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .entries
            .sortedByDescending { it.value }
            .associate { it.toPair() }
    }
}