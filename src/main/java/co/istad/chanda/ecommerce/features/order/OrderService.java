package co.istad.chanda.ecommerce.features.order;

import co.istad.chanda.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.chanda.ecommerce.features.order.dto.OrderResponse;
import co.istad.chanda.ecommerce.features.order.dto.UpdateOrderRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {
    OrderResponse create(String customerId, CreateOrderRequest request);
    OrderResponse update(UUID id, UpdateOrderRequest request);
    Page<OrderResponse> findAll(int pageNumber, int pageSize);
    Page<OrderResponse> findMine(String customerId, int pageNumber, int pageSize);
    OrderResponse findById(UUID id);
    void delete(UUID id);
}
