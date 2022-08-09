package com.sokkun.smallfasttransfer.api.exception

class IdNotFoundException(name: String, id: Long) : RuntimeException("The $name id[$id]")