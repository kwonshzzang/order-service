package kr.co.kwonshzzang.orderservice.order.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        // 널 값을 가질 수 없고 최소한 화이트 스페이스가 아닌 문자를 하나 이상 가져야 한다.
        @NotBlank(message = "The book ISBN must be defined.")
        String isbn,

        // 널 값을 가질 수 없고 1부터 5사이의 값을 가져야 한다.
        @NotNull(message = "The book quantity must be defined.")
        @Min(value =1, message = "You must order at least 1 item.")
        @Max(value = 5, message = "You cannot order more than 5 items")
        Integer quantity
) {
}
