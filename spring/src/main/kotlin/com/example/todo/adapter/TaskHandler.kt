package com.example.todo.adapter

import com.example.todo.application.*
import org.springframework.stereotype.*
import org.springframework.web.servlet.function.*
import java.net.*

@Component
class TaskHandler(
  private val taskService: TaskService
) {

  fun create(
    req: ServerRequest
  ): ServerResponse {
    val listId = req.id("listId")
    val taskId = taskService.create(
      listId, req.body()
    )
    return ServerResponse.created(
      URI.create("/$listId/$taskId/")
    ).build()
  }

  fun findById(
    req: ServerRequest
  ): ServerResponse {
    val id = req.id("taskId")
    return taskService.findById(id)
      .map {
        ServerResponse.ok().body(it)
      }.orElse(
        ServerResponse.notFound()
          .build()
      )
  }

  fun update(
    req: ServerRequest
  ): ServerResponse {
    val id = req.id("taskId")
    taskService.update(id, req.body())
    return ServerResponse.ok().build()
  }

  fun delete(
    req: ServerRequest
  ): ServerResponse {
    val listId = req.id("listId")
    val taskId = req.id("taskId")
    taskService.delete(listId, taskId)
    return ServerResponse.noContent()
      .build()
  }
}

