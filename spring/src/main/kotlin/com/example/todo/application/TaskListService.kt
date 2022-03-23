package com.example.todo.application

import com.example.todo.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import java.util.*
import kotlin.NoSuchElementException

@Service
@Transactional
class TaskListService(
  private val listRepository:
  TaskListRepository
) {

  fun create(
    dto: CreateTaskListDto
  ): Long {
    val list = TaskList(dto.title)
    val savedList =
      listRepository.save(list)
    return savedList.id
  }

  fun findAll(): List<ListOverviewDto> {
    return listRepository
      .findAll()
      .map {
        ListOverviewDto(
          it.id, it.title
        )
      }
  }

  fun findById(
    id: Long
  ): Optional<TaskListDto> =
    listRepository.findById(id)
      .map { list ->
        TaskListDto(
          list.title,
          list.tasks.map { task ->
            TaskDto(
              task.id,
              task.title,
              task.dueDate,
              task.isDone
            )
          })
      }

  fun rename(
    id: Long,
    dto: RenameTaskListDto
  ) {
    val list =
      listRepository.findById(id)
        .orElseThrow {
          NoSuchElementException()
        }
    list.title = dto.title
  }

  fun delete(id: Long) {
    listRepository.findById(id)
      .ifPresent { list ->
        listRepository.delete(list)
      }
  }
}