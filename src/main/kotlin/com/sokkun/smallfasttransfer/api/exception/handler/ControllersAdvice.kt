package com.sokkun.smallfasttransfer.api.exception.handler

import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.MethodArgumentNotValidException

@ConditionalOnProperty(name = ["application.events.enabled"], havingValue = "true")
@ControllerAdvice
class ControllersAdvice {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun argumentNotValidException(
        request: HttpServletRequest?,
        e: MethodArgumentNotValidException
    ): ResponseEntity<ResponseWrapper<Any>> {
        log.error("MethodArgumentNotValidException: ", e)

        val body = e.bindingResult.fieldError?.let { fieldError ->
            fieldError.defaultMessage?.let { message ->
                when {
                    message.contains("must not be empty") -> {
                        ResponseWrapper.error(
                            error = ErrorCode.MISSING_VALUE_OF_REQUIRED_KEY,
                            message = fieldError.field
                        )
                    }
                    message.contains("invalid format") -> {
                        ResponseWrapper.error(
                            error = ErrorCode.INVALID_INPUT_FORMAT,
                            message = fieldError.field
                        )
                    }
                    else -> {
                        ResponseWrapper.error(
                            error = ErrorCode.DYNAMIC_MESSAGE,
                            message = """${fieldError.field} ${fieldError.defaultMessage}"""
                        )
                    }
                }
            } ?: ResponseWrapper.error(error = ErrorCode.INTERNAL_ERROR, message = e.toString())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }
}