package com.socialmedia.mapper;

import com.socialmedia.dto.response.UserProfileFindAllResponseDto;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.repository.entity.UserProfile;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IElasticMapper {

    IElasticMapper INSTANCE = Mappers.getMapper(IElasticMapper.class);

    List<UserProfile> toUserProfiles(List<UserProfileFindAllResponseDto> dtos);

    UserProfile toUserProfile(RegisterElasticModel model);


}
