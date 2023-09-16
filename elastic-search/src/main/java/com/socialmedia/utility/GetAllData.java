package com.socialmedia.utility;

import brave.Response;
import com.socialmedia.dto.response.UserProfileFindAllResponseDto;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IElasticMapper;
import com.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final IUserManager userManager;
    private final UserService userService;

    //@PostConstruct // ilk çalıştırırken açtık bütün verileri aldık. eski verileri..
    public void initData(){
        List<UserProfileFindAllResponseDto> list=userManager.findAll().getBody();
        //List<UserProfile>
        userService.saveAll(IElasticMapper.INSTANCE.toUserProfiles(list));
    }
}
