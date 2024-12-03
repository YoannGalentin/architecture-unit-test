package io.github.yoanngalentin.app.data

import io.github.yoanngalentin.app.model.Todo
import org.springframework.data.repository.reactive.ReactiveCrudRepository

// TODO: test coroutine reactive
// interface TodoListRepository : CoroutineCrudRepository<Todo, String>

interface TodoListRepository : ReactiveCrudRepository<Todo, String>
