package com.snowman.project.service

import com.snowman.project.dao.goal.GoalRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GoalService(
    val goalRepository: GoalRepository
) {
}