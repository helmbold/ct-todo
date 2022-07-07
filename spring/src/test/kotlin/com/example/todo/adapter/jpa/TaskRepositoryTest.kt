package com.example.todo.adapter.jpa

import com.example.todo.domain.Task
import com.example.todo.domain.TaskRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class TaskRepositoryTest {

  @Autowired
  lateinit var repository: TaskRepository

  @Autowired
  lateinit var entityManager: EntityManager

  @Test
  fun shouldPersistTask() {
    val date = LocalDateTime.of(
      2020, 3, 23, 12, 0, 0, 0
    ).atOffset(
      ZoneOffset.systemDefault()
        .rules
        .getOffset(LocalDateTime.now())
    )
    val task = Task(
      title = "Streichen",
      dueDate = date,
      isDone = true
    )

    repository.save(task)
    entityManager.flush()
    entityManager.clear()

    val taskFromDb =
      repository.findById(task.id)
        .orElseThrow()
    assertEquals(
      "Streichen", taskFromDb.title)
    assertEquals(
      date, taskFromDb.dueDate
    )
    assertTrue(taskFromDb.isDone)
  }
}