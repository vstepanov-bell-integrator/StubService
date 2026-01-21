package com.vvstepanov.stubservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostUserDto {
    private String login;
    private String password;
    private String date;
}
