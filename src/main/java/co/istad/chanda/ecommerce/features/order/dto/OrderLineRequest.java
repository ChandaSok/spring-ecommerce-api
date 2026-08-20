package co.istad.chanda.ecommerce.features.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderLineRequest(
        @NotNull Integer productId,
        @NotNull @Min(1) Integer qty
) {}
