package com.vvstepanov.stubservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostUserDto {
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 20, message = "Длина логина должна быть от 3 до 20 символов")
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 30, message = "Длина пароля должна быть от 6 до 30 символов")
    private String password;
    private String date;
}
