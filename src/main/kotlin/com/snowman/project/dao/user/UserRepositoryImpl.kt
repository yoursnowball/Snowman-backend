package com.snowman.project.dao.user

import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.snowman.project.model.todo.entity.QTodo.todo
import com.snowman.project.model.user.entity.QUser.user
import com.snowman.project.model.user.entity.User
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class UserRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {

    override fun findUserNotWriteTomorrowTodoYet(tomorrow: LocalDate): List<User> {
        return queryFactory.select(
            user
        )
            .from(user)
            .where(
                user.fcmToken.isNotNull,
                JPAExpressions.select(todo.count())
                    .from(todo)
                    .where(todo.user.eq(user), todo.todoDate.eq(tomorrow)).eq(0)
            ).fetch()
    }

    override fun findUserNotCompleteTodayTodoYet(): List<User> {
        return queryFactory.select(
            user
        )
            .from(user)
            .where(
                user.fcmToken.isNotNull,
                JPAExpressions.select(todo.count())
                    .from(todo)
                    .where(todo.user.eq(user), todo.todoDate.eq(LocalDate.now()), todo.succeed.isFalse).gt(0)
            ).fetch()
    }
}