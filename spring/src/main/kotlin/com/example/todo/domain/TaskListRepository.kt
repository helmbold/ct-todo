package com.example.todo.domain

import org.springframework.data.repository.*

interface TaskListRepository :
  CrudRepository<TaskList, Long>