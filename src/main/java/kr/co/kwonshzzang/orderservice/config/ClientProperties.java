package kr.co.kwonshzzang.orderservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "polar")
// 사용자 지정 속성 이름의 프리픽스
public record ClientProperties(

        // 카탈로그 서비스의 URI를 지정하는 속성. 널 값을 가질 수 없다.
        @NotNull
        URI catalogServiceUri,
        String greeting  // 사용자 정의 속성인 polar.greeting(프리픽스 + 필드명) 속성이 문자열로 인식되는 필드
) {
}
