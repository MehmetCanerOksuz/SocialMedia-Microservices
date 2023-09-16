package com.socialmedia.manager;

import com.socialmedia.dto.request.AuthUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.socialmedia.constant.EndPoint.*;

@FeignClient(
        url = "${feign.auth}", decode404 = true, name = "userprofile-auth"
)
public interface IAuthManager {

    @PutMapping(UPDATE)
    ResponseEntity<String> updateAuth(@RequestBody AuthUpdateRequestDto dto, @RequestHeader("Authorization") String token);

}
