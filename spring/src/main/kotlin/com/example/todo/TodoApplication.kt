package com.example.todo

import com.example.todo.adapter.TaskHandler
import com.example.todo.adapter.TaskListHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.router

@SpringBootApplication
@Configuration
class TodoApplication {

  @Bean
  fun router(
    taskListHandler: TaskListHandler,
    taskHandler: TaskHandler
  ) =
    router {
      accept(APPLICATION_JSON).nest {
        filter { request, next ->
          try {
            next(request)
          } catch (
            _: NoSuchElementException
          ) {
            status(NOT_FOUND).build()
          }
        }
        POST("/", taskListHandler::create)
        GET("/", taskListHandler::findAll)
        GET("/{listId}", taskListHandler::findById)
        PUT("/{listId}/title", taskListHandler::rename)
        DELETE("/{listId}", taskListHandler::delete)
        POST("/{listId}", taskHandler::create)
        GET("/{listId}/{taskId}", taskHandler::findById)
        PUT("/{listId}/{taskId}", taskHandler::update)
        DELETE("/{listId}/{taskId}", taskHandler::delete)
      }
    }
}

fun main(args: Array<String>) {
  runApplication<TodoApplication>(*args)
}
