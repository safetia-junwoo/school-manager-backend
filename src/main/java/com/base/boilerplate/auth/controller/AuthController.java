package com.base.boilerplate.auth.controller;

import com.base.boilerplate.auth.dto.LoginDTO;
import com.base.boilerplate.auth.dto.LoginSuccessDTO;
import com.base.boilerplate.auth.dto.TokenDTO;
import com.base.boilerplate.auth.dto.UserDTO;
import com.base.boilerplate.auth.exception.CustomAuthException;
import com.base.boilerplate.auth.service.AuthService;
import com.base.boilerplate.config.GlobalSettings;
import com.base.boilerplate.util.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Tag(name = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenUtils tokenUtils;
    private final GlobalSettings globalSettings;
    @Operation(description = "회원 로그인")
    @Parameters({
            @Parameter(name = "userLoginId", description = "로그인 아이디", example = "dev123"),
            @Parameter(name = "userPassword", description = "로그인 비밀번호", example = "12345")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "로그인 회원 정보 오류", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginSuccessDTO loginSuccessDTO = authService.userLogin(loginDTO, request);

        response.setHeader(HttpHeaders.SET_COOKIE, "SameSite="+globalSettings.getCookieOption());
        response.addCookie(tokenUtils.buildTokenCookie("accessToken", loginSuccessDTO.getTokenInfo().getAccessToken()));
        response.addCookie(tokenUtils.buildTokenCookie("refreshToken", loginSuccessDTO.getTokenInfo().getRefreshToken()));
        return ResponseEntity.ok().body(loginSuccessDTO.getUserInfo());
    }
    @Operation(description = "회원 로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(schema = @Schema(hidden = true)))
    @PutMapping("/logout")
    public ResponseEntity<?> userLogout(HttpServletResponse response){
        authService.deleteSecurityContext();

        response.setHeader(HttpHeaders.SET_COOKIE, "SameSite="+globalSettings.getCookieOption());
        return ResponseEntity.ok().body(null);
    }
    @Operation(description = "리프레쉬 토큰 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "리프레쉬 토큰 검증 오류", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response){
        TokenDTO token = authService.refreshToken(request);

        response.setHeader(HttpHeaders.SET_COOKIE, "SameSite="+globalSettings.getCookieOption());
        response.addCookie(tokenUtils.buildTokenCookie("accessToken", token.getAccessToken()));
        response.addCookie(tokenUtils.buildTokenCookie("refreshToken", token.getRefreshToken()));
        return ResponseEntity.ok().body(null);
    }

    @Operation(description = "로그인 상태 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 상태", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "비로그인 상태", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/check-login")
    public ResponseEntity<?> checkLogin(HttpServletRequest request) throws CustomAuthException {
        UserDTO userDTO = authService.checkLogin(request);
        return ResponseEntity.ok().body(userDTO);
    }

//    @PostMapping("/sso-login")
//    public ResponseEntity<?> userSso(HttpServletRequest request,  HttpServletResponse response) throws Exception {
//        System.out.println("SSO 로그인 확인");
//        String loginId = authSsoService.handleSsoLogin(request);
//        System.out.println("타임아웃 확인");
//        LoginSuccessDTO loginSuccessDTO = authService.userLoginSso(loginId, request);
//        if(loginSuccessDTO!=null){
//            response.setHeader(HttpHeaders.SET_COOKIE, "SameSite="+globalSettings.getCookieOption());
//            response.addCookie(tokenUtils.buildTokenCookie("accessToken", loginSuccessDTO.getTokenInfo().getAccessToken()));
//            response.addCookie(tokenUtils.buildTokenCookie("refreshToken", loginSuccessDTO.getTokenInfo().getRefreshToken()));
//            return ResponseEntity.status(HttpStatus.FOUND).body(loginSuccessDTO.getUserInfo());
//        }else{
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }



}