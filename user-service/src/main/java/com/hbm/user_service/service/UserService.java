package com.hbm.user_service.service;

import com.hbm.user_service.dto.RegistrationRequest;
import com.hbm.user_service.dto.common.UserResponse;
import com.hbm.user_service.model.User;
import com.hbm.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;

public interface UserService {
    UserResponse registerUser(RegistrationRequest request);
}
