package kr.co.kwonshzzang.orderservice.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .bodyToMono(Book.class); // 받은 객체를 Mono<Book>으로 반환한다.
    }

}
