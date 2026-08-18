package com.hbm.user_service.service;


import com.hbm.user_service.dto.RegistrationRequest;
import com.hbm.user_service.dto.common.UserResponse;
import com.hbm.user_service.mapper.UserMapper;
import com.hbm.user_service.model.Role;
import com.hbm.user_service.model.User;
import com.hbm.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponse registerUser(RegistrationRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dob(request.dob())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .roles(request.roles().stream().map(Role::new).toList())
                .publicId(UUID.randomUUID())
                .build();

        User savedUser= repository.save(user);
        return mapper.toResponse(savedUser);
    }
}
