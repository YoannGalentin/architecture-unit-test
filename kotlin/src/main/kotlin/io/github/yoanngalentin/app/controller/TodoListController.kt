
package io.github.yoanngalentin.app.controller

import io.github.yoanngalentin.app.data.TodoListRepository
import io.github.yoanngalentin.app.model.Todo
import io.github.yoanngalentin.app.service.TodoListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/todo")
class TodoListController(
    val todoListService: TodoListService,
    val todoListRepository: TodoListRepository,
) {
    @GetMapping
    fun list(): Mono<ResponseEntity<List<Todo>>> =
        todoListService
            .list()
            .map { ResponseEntity.ok(it) }
            .onErrorReturn(ResponseEntity.internalServerError().build())

    @PostMapping
    fun addTodo(
        @RequestBody todoToAdd: Todo,
    ): Mono<ResponseEntity<Unit>> =
        todoListRepository
            .save(todoToAdd)
            .map { }
            .map { ResponseEntity.ok(it) }
            .onErrorReturn(ResponseEntity.internalServerError().build())
}
