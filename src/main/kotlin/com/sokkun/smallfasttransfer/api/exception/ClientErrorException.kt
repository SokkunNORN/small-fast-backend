package com.sokkun.smallfasttransfer.api.exception

import com.sokkun.smallfasttransfer.api.exception.handler.ErrorCode

data class ClientErrorException(val errorCode: ErrorCode, val description: String?) : RuntimeException(description)
