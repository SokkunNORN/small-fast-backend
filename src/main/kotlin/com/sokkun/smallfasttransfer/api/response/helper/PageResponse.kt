package com.sokkun.smallfasttransfer.api.response.helper

data class PageResponse<T>(var content: List<T>?, var pagination: Pagination,)

data class Pagination(val currentPage: Int, val pageSize: Int, val totalElements: Long, val totalPages: Int)
