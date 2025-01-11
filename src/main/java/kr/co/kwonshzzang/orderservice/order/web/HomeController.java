package kr.co.kwonshzzang.orderservice.order.web;

import kr.co.kwonshzzang.orderservice.config.ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// REST/HTTP 엔드포인트를 위한 핸들러를 정의하는 클래스로 인식
@RequiredArgsConstructor
public class HomeController {
    private final ClientProperties clientProperties;
    // 사용자 정의 속성 액세스를 위해 생성자 오토와이어링을 통해 주입된 빈

    @GetMapping("/")
    //루트 엔드포인트로 GET 요청을 처리
    public String getGreeting() {
        return clientProperties.greeting(); //설정 데이터 빈에서 가져온 환영 메시지를 사용
    }
}
