package com.base.boilerplate.auth.service;

import com.base.boilerplate.api.sample.domain.model.Sample;
import com.base.boilerplate.api.sample.dto.SampleRequestDTO;
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
public class UserService {

    private final UserRepository userRepository;


    public Integer upsertItem(UserRequestDTO requestDto) {
        ComUser newItem = userRepository.save(ComUser.convertToEntity(requestDto));
        requestDto.setId(newItem.getId());
        return requestDto.getId();
    }

}
