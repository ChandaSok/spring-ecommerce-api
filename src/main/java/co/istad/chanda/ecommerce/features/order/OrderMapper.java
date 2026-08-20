package co.istad.chanda.ecommerce.features.order;

import co.istad.chanda.ecommerce.features.order.dto.OrderLineResponse;
import co.istad.chanda.ecommerce.features.order.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        var lines = order.getOrderLines().stream().map(line -> new OrderLineResponse(
                line.getId(), line.getProduct().getId(), line.getProduct().getCode(),
                line.getProduct().getName(), line.getQty(), line.getUnitPrice(),
                line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQty()))
        )).toList();
        BigDecimal subtotal = lines.stream().map(OrderLineResponse::subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = BigDecimal.valueOf(order.getDiscount())
                .divide(BigDecimal.valueOf(100));
        return new OrderResponse(order.getUuid(), order.getCustomerId(), order.getDiscount(),
                order.getOrderedAt(), order.getAddress(), order.getStatus(), order.getRemark(),
                subtotal.subtract(subtotal.multiply(discount)), lines);
    }
}
