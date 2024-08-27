package kr.co.kwonshzzang.orderservice.order.web;

import jakarta.validation.Valid;
import kr.co.kwonshzzang.orderservice.order.domain.Order;
import kr.co.kwonshzzang.orderservice.order.domain.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Mono<Order> submitOrder(
            @RequestBody @Valid OrderRequest orderRequest) {
        return orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity());
    }
}
