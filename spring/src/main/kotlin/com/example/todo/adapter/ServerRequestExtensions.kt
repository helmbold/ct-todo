package com.example.todo.adapter

import org.springframework.web.servlet.function.*

fun ServerRequest.id(name: String) =
  this.pathVariable(name)
    .toLongOrNull() ?: -1