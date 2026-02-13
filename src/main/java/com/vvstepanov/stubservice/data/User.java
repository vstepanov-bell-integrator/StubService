package com.vvstepanov.stubservice.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 20, message = "Длина логина должна быть от 3 до 20 символов")
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 30, message = "Длина пароля должна быть от 6 до 30 символов")
    private String password;

    @NotBlank(message = "e-mail не может быть пустым")
    @Size(min = 6, max = 50, message = "Длина e-mail должна быть от 6 до 50 символов")
    private String email;
    private String date;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
