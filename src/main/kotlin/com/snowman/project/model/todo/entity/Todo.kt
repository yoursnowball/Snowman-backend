package com.snowman.project.model.todo.entity

import com.snowman.project.model.common.entity.BaseTimeEntity
import com.snowman.project.service.common.event.Events
import com.snowman.project.model.goal.entity.Goal
import com.snowman.project.service.todo.event.TodoCheckedUpdateEvent
import com.snowman.project.service.todo.event.TodoUnCheckedUpdateEvent
import com.snowman.project.model.user.entity.User
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "todos")
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "goal_id")
    val goal: Goal,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "name", length = 20, nullable = false)
    var name: String,

    @Column(name = "succeed", nullable = false)
    var succeed: Boolean = false,

    @Column(name = "finished_at")
    var finishedAt: LocalDateTime? = null,

    @Column(name = "todo_date")
    val todoDate: LocalDate


) : BaseTimeEntity() {

    fun update(name: String, succeed: Boolean){
        this.name = name
        /**
         * UnCheck -> Check
         */
        if (!this.succeed && succeed) {
            this.succeed = succeed
            this.finishedAt = LocalDateTime.now()
            Events.raise(TodoCheckedUpdateEvent(this))
        }
        /**
         * Check -> Uncheck
         */
        else if (this.succeed && !succeed) {
            this.succeed = succeed
            this.finishedAt = null
            Events.raise(TodoUnCheckedUpdateEvent(this))
        }
    }

    fun canUpdateOrDelete(): Boolean {
        return todoDate.isAfter(LocalDate.now()) || todoDate.isEqual(LocalDate.now())
    }
}
