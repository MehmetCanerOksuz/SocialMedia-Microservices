package com.socialmedia.convertor;

import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.repository.entity.Auth;

public class Convertor {

    public static Auth convertFromDtoToAuth(RegisterRequestDto dto){

        if (dto == null){
            throw new RuntimeException("Dto Null Geldi");
        }

        Auth auth = Auth.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();
        return auth;
    }
}
