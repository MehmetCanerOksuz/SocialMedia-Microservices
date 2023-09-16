package com.socialmedia.mapper;

import com.socialmedia.dto.request.AuthUpdateRequestDto;
import com.socialmedia.dto.request.UserProfileUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileFindAllResponseDto;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(UserSaveRequestDto dto);
    UserProfile toUserProfile(RegisterModel model);

    UserProfile toUserProfile(UserProfileUpdateRequestDto dto);
    @Mapping(source = "authId", target = "id")
    AuthUpdateRequestDto toAuthUpdateRequestDto(UserProfile userProfile);

//    @Mapping(source = "id", target = "userProfileId") // sonradan id olarak değiştirildi..
    UserProfileFindAllResponseDto toUserProfileFindAllResponseDto(UserProfile userProfile);

    RegisterElasticModel toRegeRegisterElasticModel(UserProfile userProfile);

}
