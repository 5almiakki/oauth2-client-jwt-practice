package s5almiakki.oauth2clientjwtpractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainController {

    @GetMapping("/")
    public Map<String, Object> mainApi() {
        return Map.of("message", "Main API");
    }
}
