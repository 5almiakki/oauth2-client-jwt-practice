package s5almiakki.oauth2clientjwtpractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    @GetMapping("/my")
    public Map<String, Object> myApi() {
        return Map.of("message", "My API");
    }
}
