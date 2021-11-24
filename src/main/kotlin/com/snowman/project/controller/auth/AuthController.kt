package com.snowman.project.controller.auth

import com.snowman.project.controller.auth.req.SignInRequest
import com.snowman.project.controller.auth.req.SignUpRequest
import com.snowman.project.controller.auth.res.TokenResponse
import com.snowman.project.service.auth.AuthService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
        val authService: AuthService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("회원가입")
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody req: SignUpRequest): TokenResponse {
        return TokenResponse(authService.signUp(req.userName, req.password, req.nickName))
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    fun signIn(
            @Valid @RequestBody req: SignInRequest
    ): TokenResponse {
        return TokenResponse(authService.signIn(req.userName, req.password))
    }
}