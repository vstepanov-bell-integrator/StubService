package com.vvstepanov.stubservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "stub.response")
public class StubProperties {
    private int delayMin = 1000;
    private int delayMax = 2000;
}
