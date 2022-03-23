package com.example.todo.adapter

import com.example.todo.application.*
import org.springframework.stereotype.*
import org.springframework.web.servlet.function.*
import java.net.*

@Component
class TaskListHandler(
  private val service: TaskListService
) {

  fun create(
    req: ServerRequest
  ): ServerResponse {
    val id =
      service.create(req.body())
    return ServerResponse
      .created(URI.create("/$id/"))
      .build()
  }

  fun findAll(req: ServerRequest) =
    ServerResponse
      .ok()
      .body(service.findAll())

  fun findById(
    req: ServerRequest
  ): ServerResponse =
    service
      .findById(req.id("listId"))
      .map {
        ServerResponse.ok().body(it)
      }.orElse(
        ServerResponse.notFound()
          .build()
      )

  fun rename(
    req: ServerRequest
  ): ServerResponse {
    val id = req.id("listId")
    service.rename(id, req.body())
    return ServerResponse.noContent()
      .build()
  }

  fun delete(
    req: ServerRequest
  ): ServerResponse {
    val id = req.id("listId")
    service.delete(id)
    return ServerResponse.noContent()
      .build()
  }
}