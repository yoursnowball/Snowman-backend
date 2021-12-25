package com.snowman.project.controller.todo.res

import com.snowman.project.model.goal.enums.LevelChange
import com.snowman.project.model.todo.dto.TodoInfoDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel("투두 상세")
data class GetTodoResponse(
    @ApiModelProperty("투두 정보")
    val todo: TodoInfoDto,

    @ApiModelProperty("투두 변경 후 레벨")
    val level: Int
) {

}