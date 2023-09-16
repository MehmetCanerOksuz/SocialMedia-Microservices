package com.socialmedia.controller;

import com.socialmedia.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.socialmedia.constant.EndPoint.*;

@RestController
@RequestMapping(MAIL)
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


}
