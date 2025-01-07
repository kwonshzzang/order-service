package kr.co.kwonshzzang.orderservice.domain;

import kr.co.kwonshzzang.orderservice.book.Book;
import kr.co.kwonshzzang.orderservice.book.BookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
// 이 클래스가 스프링에 의해 관리되는 서비스임을 표시하는 스테레오타입 애너테이션
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    // 플럭스(Flux)는 여러 개의 주문을 위해 사용된다(0..N)
    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 주문 객체를 가지고 모노를 생성한다.
    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn) // 카탈로그 서비스를 호출해 책의 주문 가능성을 확인한다.
                .map(book -> buildAcceptedOrder(book, quantity)) // 책 주문이 가능하면 접수한다.
                .defaultIfEmpty( // 책이 카탈로그에 존재하지 않으면 주문을 거부한다.
                        buildRejectedOrder(isbn, quantity)
                )
                .flatMap(orderRepository::save); // 주문을 (접수 혹은 거부 상태로) 저장한다.
    }

    // 주문이 접수되면 ISBN, 책의 이름(제목과 저자), 수량, 상태만 지정하면 스프링 데이터가 식별자, 버전, 감사 메타데이터를 추가한다.
    public static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.of(book.isbn(), book.title() + "-" + book.author(),
                book.price(), quantity, OrderStatus.ACCEPTED);
    }

    // 주문이 거부되면 ISBN, 수량, 상태만 지정한다. 스프링 데이터가 식별자, 버전, 감사 메타데이터를 알아서 처리해준다.
    public static Order buildRejectedOrder(String isbn, int quantity) {
        return Order.of(isbn, null, null, quantity, OrderStatus.REJECTED);
    }
}
