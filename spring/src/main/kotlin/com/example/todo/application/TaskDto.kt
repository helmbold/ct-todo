package com.example.todo.application

import com.example.todo.domain.*
import java.time.*

data class TaskDto(
  val id: Long?,
  val title: String,
  val dueDate: OffsetDateTime? = null,
  val isDone: Boolean = false
) {
  constructor(task: Task) : this(
    task.id,
    task.title,
    task.dueDate,
    task.isDone
  )
}