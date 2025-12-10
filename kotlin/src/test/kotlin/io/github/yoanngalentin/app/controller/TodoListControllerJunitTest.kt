package io.github.yoanngalentin.app.controller

import io.github.yoanngalentin.app.data.TodoListRepository
import io.github.yoanngalentin.app.model.Todo
import io.github.yoanngalentin.app.service.TodoListService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class TodoListControllerJunitTest(
    @Autowired
    var todoListService: TodoListService,
    @Autowired
    var todoListRepository: TodoListRepository,
    @Autowired
    var webTestClient: WebTestClient,
) {
    @BeforeEach
    fun setUp() {
        todoListRepository.deleteAll().block(defaultTimeout)
    }

    @Test
    fun `should return a HTTP 200 and todos`() {
        todoListService
            .add(
                Todo(
                    id = 1,
                    title = "Do the laundry",
                    completed = false,
                ),
            ).block(defaultTimeout)

        webTestClient
            .get()
            .uri("/todo")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .json(
                """
                [{"id":1, "title": "Do the laundry", "completed":false}]
                """.trimIndent(),
            )
    }
}

//        StepVerifier
//            .create(
//                todoListService
//                    .add(
//                        Todo(
//                            id = 1,
//                            title = "Do the laundry",
//                            completed = false,
//                        ),
//                    ),
//            ).expectNext(Unit)
//            .verifyComplete()
