package kr.co.kwonshzzang.orderservice.order.web;

import kr.co.kwonshzzang.orderservice.domain.Order;
import kr.co.kwonshzzang.orderservice.domain.OrderService;
import kr.co.kwonshzzang.orderservice.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(OrderController.class) // OrderController를 대상으로 한 스프링 웹플럭스 컴포넌트에 집중하는 테스트임을 나타낸다.
class OrderControllerWebFluxTests {

    @Autowired
    private WebTestClient webTestClient; // 웹 클라이언트의 변형으로 Restful 서비스 테스트를 쉽게 하기 위한 기능을 추가로 가지고 있다.

    @MockBean
    private OrderService orderService; // OrderService의 모의 객체를 스프링 애플리케이션 컨넥스트에 추가한다.

    @Test
    void whenBookNotAvailableThenRejectedOrder() {
        var orderRequest = new OrderRequest("1234567890", 3);
        var expectedOrder = OrderService.buildRejectedOrder(orderRequest.isbn(), orderRequest.quantity());
        given(orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity()))  // OrderService 모의 빈이 어떻게 작동해야 하는지 지정한다.
                .willReturn(Mono.just(expectedOrder));

        webTestClient
                .post()
                .uri("/orders")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().is2xxSuccessful() // 주문이 성공적으로 생성될 것을 에상한다.
                .expectBody(Order.class).value(actualOrder -> {
                    assertThat(actualOrder).isNotNull();
                    assertThat(actualOrder.status()).isEqualTo(OrderStatus.REJECTED);
                });
    }

}