package co.istad.chanda.ecommerce.features.order;

import co.istad.chanda.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.chanda.ecommerce.features.order.dto.OrderResponse;
import co.istad.chanda.ecommerce.features.order.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@AuthenticationPrincipal Jwt jwt,
                                @Valid @RequestBody CreateOrderRequest request) {
        return orderService.create(jwt.getSubject(), request);
    }

    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateOrderRequest request) {
        return orderService.update(id, request);
    }

    @GetMapping
    public Page<OrderResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "25") int pageSize) {
        return orderService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/me")
    public Page<OrderResponse> findMine(@AuthenticationPrincipal Jwt jwt,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "25") int pageSize) {
        return orderService.findMine(jwt.getSubject(), pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        orderService.delete(id);
    }
}
