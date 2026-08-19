package com.hbm.user_service.service;

import com.hbm.user_service.dto.RegistrationRequest;
import com.hbm.user_service.dto.common.UserResponse;
import com.hbm.user_service.mapper.UserMapper;
import com.hbm.user_service.mapper.UserMapperImpl;
import com.hbm.user_service.model.Role;
import com.hbm.user_service.model.User;
import com.hbm.user_service.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)//default
public class UserServiceImplTest {

    @Captor
    ArgumentCaptor<User>userCaptor;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private static RegistrationRequest registrationRequest;

    private static UserResponse userResponse;

    private static User user;

    private static UUID uuid=UUID.randomUUID();;

    @BeforeAll
    public static void init(){
        LocalDate dob = LocalDate.of(1980,12,20);

        registrationRequest=new RegistrationRequest(
        "John",
        "Smith",
        dob,
        "john@gmail.com",
        "johnPwd",
        List.of("user"));

        userResponse=new UserResponse(
                "John",
                "Smith",
                dob,
                "john@gmail.com",
                uuid

        );

        user=User.builder()
                .firstName("John")
                .lastName("Smith")
                .dob(dob)
                .email("john@gmail.com")
                .publicId(uuid)
                .build();
    }

    @Test
    public void when_register_should_return_user_response_obj() {
        // 1. ARRANGE: Set up the preconditions and stub the behavior of mocks
        Mockito.when(passwordEncoder.encode(registrationRequest.password())).thenReturn("someString");
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

        Mockito.when(mapper.toResponse(user)).thenAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            UserMapper realMapper = Mappers.getMapper(UserMapper.class);
            return realMapper.toResponse(userArg);
        });

        // 2. ACT: Execute the target method under test
        UserResponse actualResponse = userService.registerUser(registrationRequest);

        // 3. ASSERT: Verify that the outcome matches the expectations
        Assertions.assertEquals(userResponse, actualResponse);

        // -- VERIFY STEPS --
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("johnPwd");
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(repository).save(userCaptor.capture());
        User capturedUser=userCaptor.getValue();
        Assertions.assertEquals(capturedUser.getPassword(),"someString");
    }



}
