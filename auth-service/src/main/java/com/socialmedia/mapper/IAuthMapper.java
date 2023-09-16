package com.socialmedia.mapper;

import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.rabbitmq.model.MailModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(RegisterRequestDto dto);

    RegisterResponseDto toRegisterRequestDto(Auth auth);
    @Mapping(source = "id", target = "authId")
    UserSaveRequestDto toUserSaveRequestDto(Auth auth);
    @Mapping(source = "id", target = "authId")
    RegisterModel toRegisterModel(Auth auth);

    MailModel toMailModel (Auth auth);
}
