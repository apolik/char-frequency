package org.polik.charfrequency.web

import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.validation.BindException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {
    val DEFAULT_DETAIL_MESSAGE = ""

    @ExceptionHandler(value = [ValidationException::class, HttpMessageConversionException::class, BindException::class, MethodArgumentNotValidException::class])
    fun handleBindExceptions(ex: Exception): ProblemDetail {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.message ?: DEFAULT_DETAIL_MESSAGE).body
    }
}