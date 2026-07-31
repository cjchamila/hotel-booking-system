package com.hbm.user_service.dto.common;

import java.util.List;

public record CollectionResponse<T,M>(
        List<T> data,
        M meta){

}

