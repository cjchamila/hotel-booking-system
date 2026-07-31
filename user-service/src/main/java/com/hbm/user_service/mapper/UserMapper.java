package com.hbm.user_service.mapper;

import com.hbm.user_service.dto.common.UserResponse;
import com.hbm.user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
// Tells MapStruct to generate UserMapperImpl and annotate it with @Component,
// allowing Spring to discover and inject it as a bean.
public interface UserMapper {
    UserResponse toResponse(User user);
}
