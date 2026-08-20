package co.istad.chanda.ecommerce.features.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID uuid,
        String customerId,
        Float discount,
        LocalDateTime orderedAt,
        String address,
        Boolean status,
        String remark,
        BigDecimal total,
        List<OrderLineResponse> orderLines
) {}
