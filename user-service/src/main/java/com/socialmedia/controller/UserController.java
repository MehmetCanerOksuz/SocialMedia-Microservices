package com.socialmedia.controller;

import com.socialmedia.dto.request.UserProfileUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileFindAllResponseDto;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.repository.enums.EStatus;
import com.socialmedia.service.UserService;
import com.socialmedia.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.socialmedia.constant.EndPoint.*;

/*

    findByStatus metodu yazıp cachleme yapalım

 */

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserSaveRequestDto dto,@RequestHeader ("Authorization") String token){
        System.out.println("Authdan gelen token =>" + token);
        return ResponseEntity.ok(userService.createNewUser(dto));
    }

    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<String> activateStatus(@RequestParam String token){
        return ResponseEntity.ok(userService.activateStatus(token));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> updateUserProfile(@RequestBody @Valid UserProfileUpdateRequestDto dto){
        return  ResponseEntity.ok(userService.updateUserProfile(dto));
    }

    @GetMapping(FIND_ALL)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserProfileFindAllResponseDto>> findAll(){
        return ResponseEntity.ok(userService.findAllUserProfile());
    }

    @GetMapping("/find_by_username/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/find_by_status/{status}")
    public ResponseEntity<List<UserProfile>> findByStatus(@PathVariable EStatus status){
        return ResponseEntity.ok(userService.findByStatus(status));
    }

    @GetMapping("/find_by_status2/{status}")
    public ResponseEntity<List<UserProfile>> findByStatus2(@PathVariable String status){
        return ResponseEntity.ok(userService.findByStatus2(status));
    }

    @DeleteMapping(DELETE_BYID)
    public ResponseEntity<String> deleteById(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.deleteUserProfile(token));
    }

    @GetMapping("/find_all_by_pageable")
    public ResponseEntity<Page<UserProfile>> findAllByPageable(int pageSize, int pageNumber
            , @RequestParam(required = false,defaultValue = "ASC") String direction
            , @RequestParam(required = false, defaultValue = "id") String sortParameter){

        return ResponseEntity.ok(userService.findAllByPageable(pageSize,pageNumber,direction,sortParameter));
    }

    @GetMapping("/find_all_by_slice")
    public ResponseEntity<Slice<UserProfile>> findAllBySlice(int pageSize, int pageNumber
            , @RequestParam(required = false,defaultValue = "ASC") String direction
            , @RequestParam(required = false, defaultValue = "id") String sortParameter){

        return ResponseEntity.ok(userService.findAllBySlice(pageSize,pageNumber,direction,sortParameter));
    }
}
