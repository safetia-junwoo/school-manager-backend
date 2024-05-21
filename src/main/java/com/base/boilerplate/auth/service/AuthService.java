package com.base.boilerplate.auth.service;

import com.base.boilerplate.auth.domain.model.ComUser;
import com.base.boilerplate.auth.domain.repository.UserRepository;
import com.base.boilerplate.auth.dto.*;
import com.base.boilerplate.auth.exception.CustomAuthException;
import com.base.boilerplate.config.jwt.TokenProvider;
import com.base.boilerplate.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TokenUtils tokenUtils;
    public LoginSuccessDTO userLogin(LoginDTO loginDTO, HttpServletRequest request) throws CustomAuthException {
        Optional<ComUser> findUser = userRepository.findByLoginId(loginDTO.getLoginId());
        try {
            if (!findUser.isPresent()) {
                throw new CustomAuthException(HttpStatus.UNAUTHORIZED, "회원 정보가 없습니다.");
            } else if (!loginDTO.getLoginPassword().equals(findUser.get().getLoginPassword())) {
                throw new CustomAuthException(HttpStatus.UNAUTHORIZED, "비밀번호가 맞지 않습니다.");
            } else {
                UserDetailsDTO userDetailsDTO = new UserDetailsDTO(findUser.get(), findUser.get().getId(), findUser.get().getComRole().getName());
                TokenDTO tokenDTO = tokenProvider.generateJwtToken(userDetailsDTO);
                UserDTO userDTO = UserDTO.convertToDTO(findUser.get());
                return new LoginSuccessDTO(userDTO, tokenDTO);
            }
        }catch (CustomAuthException customAuthException){
            throw customAuthException;
        }
    }
//    public LoginSuccessDTO userLoginSso(String loginId, HttpServletRequest request) throws CustomAuthException {
//        Optional<ComUser> findUser = userRepository.findByLoginId(loginId);
//        LoginInfo loginInfo;
//        if(findUser.isPresent()){
//            loginInfo = LoginInfo.builder().loginType(LOGIN_TYPE.SSO).targetUser(findUser.get()).loginId(loginId).requestIp(request.getRemoteAddr()).remark("로그인 성공").responseStatus(HttpStatus.OK.name()).build();
//            System.out.println(" 유저 정보 조회 성공");
//            System.out.println("findUser.get() = " + findUser.get());
//            System.out.println("findUser.get().getSecurityRole() = " + findUser.get().getSecurityRole());
//            System.out.println("findUser.get().getId() = " + findUser.get().getId());
//            System.out.println("findUser.get().getComRole().getName() = " + findUser.get().getComRole().getName());
//            UserDetailsDTO userDetailsDTO = new UserDetailsDTO(findUser.get(), findUser.get().getSecurityRole(), findUser.get().getId(), findUser.get().getComRole().getName());
//            loginInfoRepository.save(loginInfo);
//            TokenDTO tokenDTO = tokenProvider.generateJwtToken(userDetailsDTO);
//            UserDTO userDTO = userRepository.findByUserId(findUser.get().getId());
//            return new LoginSuccessDTO(userDTO, tokenDTO);
//        } else {
//            loginInfo = LoginInfo.builder().loginType(LOGIN_TYPE.SSO).targetUser(null).loginId(loginId).requestIp(request.getRemoteAddr()).remark("아이디 오류").responseStatus(HttpStatus.UNAUTHORIZED.name()).build();
//            loginInfoRepository.save(loginInfo);
//            System.out.println(" 유저 정보 조회 실패");
//            return null;
//        }
//    }

    public void deleteSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    public TokenDTO refreshToken(HttpServletRequest request) {
        String refreshToken = tokenUtils.getToken("refreshToken", request);

        tokenProvider.validateToken(refreshToken);

        String userLoginId = tokenProvider.parseClaims(refreshToken).get(tokenProvider.LOGIN_ID).toString();

        Optional<ComUser> findUser = userRepository.findByLoginId(userLoginId);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(findUser.get(), findUser.get().getId(), findUser.get().getComRole().getName());
        return tokenProvider.generateJwtToken(userDetailsDTO);
    }

    public UserDTO checkLogin(HttpServletRequest request) {
        String refreshToken = tokenUtils.getToken("accessToken", request);
        tokenProvider.validateToken(refreshToken);
        String userLoginId = tokenProvider.parseClaims(refreshToken).get(tokenProvider.LOGIN_ID).toString();
        System.out.println("userLoginId = " + userLoginId);
        Optional<ComUser> findUser = userRepository.findByLoginId(userLoginId);

        UserDTO userDTO = UserDTO.convertToDTO(findUser.get());
        userDTO.setSocialSecurityNumber(userDTO.getSocialSecurityNumber().substring(0,7));
        System.out.println("userDTO = " + userDTO);
        return userDTO;
    }

//    public UserDTO checkLoginByLoginId(String loginId){
//        Optional<ComUser> findUser = userRepository.findByLoginId(loginId);
//        return findUser.map(UserDTO::convertToDTO).orElse(null);
//    }


    public Integer getUserId(HttpServletRequest request) {
        String refreshToken = tokenUtils.getToken("accessToken", request);
        tokenProvider.validateToken(refreshToken);
        return Integer.valueOf(tokenProvider.parseClaims(refreshToken).get(tokenProvider.ID).toString());
    }
    public String getAuthKey(HttpServletRequest request) {
        String refreshToken = tokenUtils.getToken("accessToken", request);
        tokenProvider.validateToken(refreshToken);
        return tokenProvider.parseClaims(refreshToken).get(tokenProvider.AUTHORITIES_KEY).toString();
    }
    public String getRole(HttpServletRequest request) {
        String refreshToken = tokenUtils.getToken("accessToken", request);
        tokenProvider.validateToken(refreshToken);
        return tokenProvider.parseClaims(refreshToken).get(tokenProvider.ROLE).toString();
    }



}
