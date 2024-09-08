package kr.co.kwonshzzang.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "polar")  //사용자 지정 속성 이름의 프리릭스
public record ClientProperties(
        URI catalogServiceUri,   //카탈로그 서비스의 URI를 지정하는 속성. 널 값을 가질 수 없다.
        String greeting
) {
}
