package kr.co.kwonshzzang.orderservice.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class BookClient {
    private static final String BOOKS_ROOT_API = "/books/";
    private final WebClient webClient; // 이전에 설정된 WebClient 빈

    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient
                .get() // 요청은 GET 메소드를 사용한다.
                .uri(BOOKS_ROOT_API + isbn) // 요청 URI는 /books/{isbn}이다.
                .retrieve() // 요청을 보내고 응답을 받는다.
                .bodyToMono(Book.class) // 받은 객체를 Mono<Book>으로 반환한다.
                .timeout(Duration.ofSeconds(3), Mono.empty())  // GET 요청에 대해 3초의 타임아웃을 설정한다.
                                                               // 폴백(fallback)으로 빈모노 객체를 반환한다.
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty()) //404 응답을 받으면 빈 객체를 반환한다.
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(100))  // 지수 백오프를 재시도 전략으로 사용한다.
                                                                                // 100밀리초의 초기 백오프로 총 3회까지 시도한다.
                )
                .onErrorResume(Exception.class, exception -> Mono.empty());
                // 3회의 재시도 동안 오류가 발생하면 예외를 포작하고 빈 객체를 반환한다.
    }
}
