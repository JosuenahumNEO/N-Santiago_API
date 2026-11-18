package com.nsantiago.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {
    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of("nombre", "N'Santiago API", "version", "1.0.0");
    }
}
