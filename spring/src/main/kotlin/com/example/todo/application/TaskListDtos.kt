package com.example.todo.application

data class CreateTaskListDto(
  val title: String
)

data class ListOverviewDto(
  val id: Long,
  val title: String
)

data class TaskListDto(
  val title: String,
  val tasks: List<TaskDto>
)

data class RenameTaskListDto(
  val title: String
)