package com.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "Kullanıcı adı bos gecilemez!!")
    private String username;
    @NotBlank(message = "Email bos gecilemez!!")
    @Email
    private String email;
    @NotBlank(message = "Sifre bos gecilemez!!")
    @Size(min = 5, max = 32, message = "Şifre uzunlugu en az 5, en fazla 32 karakter olabilir!!")
    //@Pattern(regexp = "^.*(?=.{5,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")
    private String password;
}
