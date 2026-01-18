package com.vvstepanov.stubservice;

import com.vvstepanov.stubservice.dto.CreateUserResponse;
import com.vvstepanov.stubservice.dto.UserRequest;
import com.vvstepanov.stubservice.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/user")
    public UserResponse getUser() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new UserResponse("Login1", "ok");
    }

    @PostMapping("/user")
    public CreateUserResponse createUser(@RequestBody UserRequest userRequest) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String currentDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new CreateUserResponse(userRequest.getLogin(), userRequest.getPassword(), currentDate);
    }
}
