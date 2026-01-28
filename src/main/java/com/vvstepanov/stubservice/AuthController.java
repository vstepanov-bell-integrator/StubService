package com.vvstepanov.stubservice;

import com.vvstepanov.stubservice.config.StubProperties;
import com.vvstepanov.stubservice.dto.GetUserDto;
import com.vvstepanov.stubservice.dto.PostUserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final StubProperties stubProperties;
    private final Random random = new Random();

    @Autowired
    public AuthController(StubProperties stubProperties) {
        this.stubProperties = stubProperties;
    }

    public void addRandomDelay() {
        int minDelay = stubProperties.getDelayMin();
        int maxDelay = stubProperties.getDelayMax();

        int delay = random.nextInt(maxDelay - minDelay + 1) + minDelay;

        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<GetUserDto> getUser() {
        addRandomDelay();

        GetUserDto userDto = new GetUserDto("Login1", "ok");

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/user")
    public ResponseEntity<PostUserDto> createUser(@Valid @RequestBody PostUserDto userDto) {
        addRandomDelay();

        ZoneId myZone = ZoneId.of("America/New_York");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(myZone);

        String currentDate = zonedDateTime
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userDto.setDate(currentDate);

        String timeZoneName = java.util.TimeZone.getDefault().getDisplayName();
        String timeZoneId = java.time.ZoneId.systemDefault().toString();

        userDto.setTimeZoneName(timeZoneName);
        userDto.setTimeZoneId(timeZoneId);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}