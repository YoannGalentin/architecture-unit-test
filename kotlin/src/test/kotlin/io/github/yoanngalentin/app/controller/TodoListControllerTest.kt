package io.github.yoanngalentin.app.controller

import io.github.yoanngalentin.app.data.TodoListRepository
import io.github.yoanngalentin.app.model.Todo
import io.github.yoanngalentin.app.service.TodoListService
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration

val defaultTimeout = Duration.ofSeconds(5)

@SpringBootTest
@AutoConfigureWebTestClient
class TodoListControllerTest(
    @Autowired
    var todoListService: TodoListService,
    @Autowired
    var todoListRepository: TodoListRepository,
    private val webTestClient: WebTestClient = mockk(),
) : FreeSpec({
        beforeAny { case ->
            todoListRepository.deleteAll().block(defaultTimeout)
        }

        "get" - {
            "list to-do should return an empty list" {
                webTestClient
                    .get()
                    .uri("/todo")
                    .exchange()
                    .expectStatus()
                    .isOk
            }

            "list to-do should return a list with one element" {
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

        "post" - {
            "return add todo in the todo list" {
                todoListService.list().block(defaultTimeout) shouldBe emptyList()

                webTestClient
                    .post()
                    .uri("/todo")
                    .contentType(APPLICATION_JSON)
                    .bodyValue(
                        """
                        {"id": 1, "title": "Do the laundry", "completed":false}
                        """.trimIndent(),
                    ).exchange()
                    .expectStatus()
                    .isOk

                todoListService.list().block(defaultTimeout) shouldBe
                    listOf(
                        Todo(
                            id = 1,
                            title = "Do the laundry",
                            completed = false,
                        ),
                    )
            }
        }
    })
