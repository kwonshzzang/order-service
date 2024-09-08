package kr.co.kwonshzzang.orderservice;

import kr.co.kwonshzzang.orderservice.config.ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final ClientProperties clientProperties;

    @GetMapping("/")
    public String getGreeting() {
        return clientProperties.greeting();
    }

}
