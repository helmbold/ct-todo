package com.example.todo.domain

import java.time.*
import javax.persistence.*

@Entity
class Task(
  var title: String,
  var dueDate: OffsetDateTime?,
  var isDone: Boolean,
) {
  @Id
  @GeneratedValue(
    strategy = GenerationType.IDENTITY
  )
  val id: Long = 0
}