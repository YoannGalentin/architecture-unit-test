package io.github.yoanngalentin.app.service

import io.github.yoanngalentin.app.data.TodoListRepository
import io.github.yoanngalentin.app.model.Todo
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TodoListService(
    val todoListRepository: TodoListRepository,
) {
    fun list(): Mono<List<Todo>> = todoListRepository.findAll().collectList()

    fun add(todo: Todo): Mono<Unit> = todoListRepository.save(todo).map { }
}
