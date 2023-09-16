package com.socialmedia.controller;

import com.socialmedia.dto.request.ActivateRequestDto;
import com.socialmedia.dto.request.AuthUpdateRequestDto;
import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.repository.entity.Auth;
import com.socialmedia.service.AuthService;
import com.socialmedia.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.socialmedia.constant.EndPoint.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(REGISTER+"_with_rabbitmq")
    public ResponseEntity<RegisterResponseDto> registerWithRabbitMq(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.registerWithRabbitMq(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<String> activateStatus(@RequestBody ActivateRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @GetMapping("/create_token")
    public ResponseEntity<String> createToken(Long id){
        return ResponseEntity.ok(jwtTokenManager.createToken(id).get());
    }

    @GetMapping("/get_id_from_token")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }

    @PutMapping(UPDATE)
    public  ResponseEntity<String> updateAuth(@RequestBody AuthUpdateRequestDto dto,@RequestHeader("Authorization") String token){

        return  ResponseEntity.ok(authService.updateAuth(dto));
    }

    @GetMapping(FIND_ALL)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Auth>> findAll(){

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(authService.findAll());
    }


    @GetMapping("/redis")
    @Cacheable(value = "redisexample")
    @PreAuthorize("hasAuthority('USER')")
    public String redisExample(String value){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    @GetMapping("/redis_delete")
    @CacheEvict(cacheNames = "redisexample", allEntries = true )
    public void redisDelete(){
    }

    @GetMapping("/redis_delete2")
    public String redisDelete2(String value){
        cacheManager.getCache("redisexample").clear();

        try {
            cacheManager.getCache("redisexample").evict(value);
            return value + " temizlendi";
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping(DELETE_BYID)
    public ResponseEntity<String> deleteById(@RequestParam String token){
        return ResponseEntity.ok(authService.deleteAuth(token));
    }
}
