package com.example.todo.application

import com.example.todo.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import kotlin.NoSuchElementException

@Service
@Transactional
class TaskService(
  private val taskRepository:
  TaskRepository,
  private val listRepository:
  TaskListRepository
) {

  fun create(
    listId: Long,
    taskDto: TaskDto
  ): Long {
    val task = Task(
      taskDto.title,
      taskDto.dueDate,
      taskDto.isDone
    )
    val savedTask =
      taskRepository.save(task)
    val list = listRepository
      .findById(listId)
      .orElseThrow {
        NoSuchElementException()
      }
    list.tasks.add(savedTask)
    return savedTask.id
  }

  fun findById(taskId: Long) =
    taskRepository.findById(taskId)
      .map { task -> TaskDto(task) }

  fun update(
    id: Long, taskDto: TaskDto
  ) {
    val task = taskRepository
      .findById(id)
      .orElseThrow {
        NoSuchElementException()
      }
    task.title = taskDto.title
    task.dueDate = taskDto.dueDate
    task.isDone = taskDto.isDone
  }

  fun delete(
    listId: Long,
    taskId: Long
  ) {
    taskRepository.findById(taskId)
      .ifPresent { task ->
        taskRepository.delete(task)
        listRepository.findById(listId)
          .ifPresent {
            it.tasks.remove(task)
          }
      }
  }
}

