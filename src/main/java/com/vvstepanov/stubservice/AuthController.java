package com.vvstepanov.stubservice;

import com.vvstepanov.stubservice.config.StubProperties;
import com.vvstepanov.stubservice.data.DatabaseWorker;
import com.vvstepanov.stubservice.data.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> getUser(@RequestParam("login") String login) {
        addRandomDelay();

        DatabaseWorker worker = new DatabaseWorker();

        User user = worker.selectUser(login);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        addRandomDelay();

        DatabaseWorker worker = new DatabaseWorker();

        worker.insertUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}