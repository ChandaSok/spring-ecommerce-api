package co.istad.chanda.ecommerce.features.order.dto;

import java.math.BigDecimal;

public record OrderLineResponse(
        Long id,
        Integer productId,
        String productCode,
        String productName,
        Integer qty,
        BigDecimal unitPrice,
        BigDecimal subTotal
) {}
