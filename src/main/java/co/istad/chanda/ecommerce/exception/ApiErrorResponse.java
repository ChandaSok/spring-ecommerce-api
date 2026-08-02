package co.istad.chanda.ecommerce.exception;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiErrorResponse<T>(
        Integer code,
        String message,
        Boolean isSuccess,
        Instant timestamp,
        T errorDetail
) {
}
