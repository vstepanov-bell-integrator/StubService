package com.vvstepanov.stubservice;

import com.vvstepanov.stubservice.dto.CreateUserResponse;
import com.vvstepanov.stubservice.dto.UserRequest;
import com.vvstepanov.stubservice.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/user")
    public UserResponse getUser() {
        return new UserResponse("Login1", "ok");
    }

    @PostMapping("/user")
    public CreateUserResponse createUser(@RequestBody UserRequest userRequest) {
        String currentDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new CreateUserResponse(userRequest.getLogin(), userRequest.getPassword(), currentDate);
    }
}
