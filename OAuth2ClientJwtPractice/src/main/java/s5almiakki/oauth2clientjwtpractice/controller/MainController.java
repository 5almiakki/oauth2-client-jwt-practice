package s5almiakki.oauth2clientjwtpractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainApi() {
        return "Main API";
    }
}
