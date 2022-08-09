package com.sokkun.smallfasttransfer.api.exception.handler

import com.sokkun.smallfasttransfer.api.exception.*
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.MethodArgumentNotValidException

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
                    message.contains("must match") -> {
                        ResponseWrapper.error(
                            error = ErrorCode.INVALID_INPUT_FORMAT,
                            message = """${fieldError.field}, ${fieldError.defaultMessage}"""
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

    @ExceptionHandler(ParamNotFoundException::class)
    fun paramNotFoundException(request: HttpServletRequest?, e: ParamNotFoundException): ResponseEntity<ResponseWrapper<Any>> {
        log.error("ParamNotFoundException: ", e)
        val response = ResponseWrapper.error(error = ErrorCode.PARAM_NOT_FOUND, message = e.message ?: "")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(IdNotFoundException::class)
    fun idNotFoundException(request: HttpServletRequest?, e: IdNotFoundException): ResponseEntity<ResponseWrapper<Any>> {
        log.error("IdNotFoundException: ", e)
        val response = ResponseWrapper.error(error = ErrorCode.ID_NOT_FOUND, message = e.message ?: "")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(request: HttpServletRequest?, e: BadRequestException): ResponseEntity<ResponseWrapper<Any>>  {
        log.error("BadRequestException: ", e)
        val response = ResponseWrapper.error(error = ErrorCode.INTERNAL_ERROR, message = e.message ?: "")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(ClientErrorException::class)
    fun clientErrorException(request: HttpServletRequest?, e: ClientErrorException): ResponseEntity<ResponseWrapper<Any>> {
        log.error("ClientErrorException: ", e)
        val response = ResponseWrapper.error(error = e.errorCode, message = e.message ?: "")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }


    @ExceptionHandler(RecordExistException::class)
    fun recordAlreadyExistsException(request: HttpServletRequest?, e: RecordExistException):
            ResponseEntity<ResponseWrapper<Any>> {
        log.error("RecordAlreadyException: ", e)
        val response = ResponseWrapper.error(error = ErrorCode.RECORD_ALREADY_EXISTED, message = e.message ?: "")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}