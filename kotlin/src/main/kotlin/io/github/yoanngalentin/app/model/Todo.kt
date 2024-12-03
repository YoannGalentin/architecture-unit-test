package io.github.yoanngalentin.app.model

import org.springframework.data.relational.core.mapping.Table

@Table("todo")
data class Todo(
    val id: Long,
    val title: String,
    val completed: Boolean,
)
