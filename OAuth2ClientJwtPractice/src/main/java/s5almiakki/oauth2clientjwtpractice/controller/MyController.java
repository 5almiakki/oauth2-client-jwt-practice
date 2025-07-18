package s5almiakki.oauth2clientjwtpractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/my")
    public String myApi() {
        return "My API";
    }
}
