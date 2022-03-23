package com.example.todo.domain

import org.springframework.data.repository.*

interface TaskRepository :
  CrudRepository<Task, Long>