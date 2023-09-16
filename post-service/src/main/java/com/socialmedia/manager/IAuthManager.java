package com.socialmedia.manager;

import com.socialmedia.dto.request.AuthUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.socialmedia.constant.EndPoint.UPDATE;

@FeignClient(
        url = "${feign.auth}", decode404 = true, name = "userprofile-auth"
)
public interface IAuthManager {

    @PutMapping(UPDATE)
    ResponseEntity<String> updateAuth(@RequestBody AuthUpdateRequestDto dto, @RequestHeader("Authorization") String token);

}
