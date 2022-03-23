package com.example.todo

import com.example.todo.application.CreateTaskListDto
import com.example.todo.application.RenameTaskListDto
import com.example.todo.application.TaskDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
internal class TaskListHandlerTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @Autowired
  lateinit var json: ObjectMapper

  @Test
  fun `create list`() {
    mockMvc.post("/") {
      contentType = APPLICATION_JSON
      content =
        json.writeValueAsString(
          CreateTaskListDto(
            title = "Urlaub"
          )
        )
    }.andExpect {
      status { isCreated() }
    }

    mockMvc.get("/").andExpect {
      jsonPath("$[0].title") {
        value("Urlaub")
      }
    }
  }

  @Test
  fun `rename list`() {
    val listPath = createList("Urlaub")

    mockMvc.put("$listPath/title") {
      contentType = APPLICATION_JSON
      content =
        json.writeValueAsString(
          RenameTaskListDto(
            "Traumurlaub"
          )
        )
    }

    mockMvc.get(listPath)
      .andExpect {
        jsonPath("$.title") {
          value("Traumurlaub")
        }
      }
  }

  @Test
  fun `delete list and tasks`() {
    val listPath =
      createList("Renovierung")
    val task1Path =
      createTask(listPath, "Streichen")
    val task2Path =
      createTask(listPath, "Abdecken")

    mockMvc.delete(listPath)

    mockMvc.get(task1Path).andExpect {
      status { isNotFound() }
    }
    mockMvc.get(task2Path).andExpect {
      status { isNotFound() }
    }
    mockMvc.get("/").andExpect {
      jsonPath("$") {
        isEmpty()
      }
    }
  }

  @Test
  fun `find missing list`() {
    mockMvc.get("/999").andExpect {
      status { isNotFound() }
    }
  }

  @Test
  fun `update missing list`() {
    mockMvc.put("/999") {
      contentType = APPLICATION_JSON
      content =
        json.writeValueAsString(
          RenameTaskListDto(
            "Neuer Name"
          )
        )
    }.andExpect {
      status { isNotFound() }
    }
  }

  private fun createTask(
    listPath: String,
    title: String
  ): String =
    mockMvc.post(listPath) {
      contentType = APPLICATION_JSON
      content =
        json.writeValueAsString(
          TaskDto(null, title)
        )
    }.andExpect {
      status { isCreated() }
    }.andReturn().response
      .getHeader("Location")!!

  private fun createList(
    title: String
  ): String =
    mockMvc.post("/") {
      contentType = APPLICATION_JSON
      content =
        json.writeValueAsString(
          CreateTaskListDto(title)
        )
    }.andExpect {
      status { isCreated() }
    }.andReturn().response
      .getHeader("Location")!!
}