package com.snowman.project.controller.system

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SystemController {

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    fun healthChecker(): String {
        return "pong"
    }
}
