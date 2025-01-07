package kr.co.kwonshzzang.orderservice.order.web;

import jakarta.validation.Valid;
import kr.co.kwonshzzang.orderservice.domain.Order;
import kr.co.kwonshzzang.orderservice.domain.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
// 클래스가 스프링 컴포넌트임을 표시하는 스테레오타입 애너테이션.
// REST 엔드포인트에 대한 핸들러가 정의되는 클래스임을 나타낸다.
@RequestMapping("orders")
// 클래스가 핸드러를 제공하는 URI의 루트패스(/orders)를 식별한다.
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Flux<Order> getAllOrders() {
        // Flux는 여러 개의 객체를 위해 사용된다.
        return orderService.getAllOrders();
    }

    @PostMapping
    public Mono<Order> submitOrder(@RequestBody @Valid OrderRequest orderRequest) {
        //OrderRequest 객체를 받아서 유효성 검증을 하고 주문을 생성한다. 생성한 주문은 모노로 반환한다.
        return orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity());
    }
}
