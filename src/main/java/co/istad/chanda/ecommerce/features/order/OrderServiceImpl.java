package co.istad.chanda.ecommerce.features.order;

import co.istad.chanda.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.chanda.ecommerce.features.order.dto.OrderResponse;
import co.istad.chanda.ecommerce.features.order.dto.UpdateOrderRequest;
import co.istad.chanda.ecommerce.features.product.Product;
import co.istad.chanda.ecommerce.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse create(String customerId, CreateOrderRequest request) {
        var productIds = new HashSet<Integer>();
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setDiscount(0F);
        order.setOrderedAt(LocalDateTime.now());
        order.setAddress(request.address());
        order.setStatus(false);
        order.setRemark(request.remark() == null ? "" : request.remark());
        order.setIsDelected(false);
        order.setOrderLines(new ArrayList<>());

        request.orderLines().forEach(item -> {
            if (!productIds.add(item.productId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A product may only appear once per order");
            }
            Product product = productRepository.findById(item.productId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID = " + item.productId() + " has not been found"));
            if (Boolean.TRUE.equals(product.getIsDeleted()) || !Boolean.TRUE.equals(product.getIsAvailable())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Product ID = " + item.productId() + " is unavailable");
            }
            if (product.getQty() < item.qty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock for product ID = " + item.productId());
            }
            product.setQty(product.getQty() - item.qty());
            OrderLine line = new OrderLine();
            line.setOrder(order);
            line.setProduct(product);
            line.setQty(item.qty());
            line.setUnitPrice(product.getUnitPrice());
            order.getOrderLines().add(line);
        });
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse update(UUID id, UpdateOrderRequest request) {
        Order order = activeOrder(id);

        // Return stock held by the old lines before validating the replacement lines.
        order.getOrderLines().forEach(line -> {
            Product product = line.getProduct();
            product.setQty(product.getQty() + line.getQty());
        });
        order.getOrderLines().clear();

        var productIds = new HashSet<Integer>();
        request.orderLines().forEach(item -> {
            if (!productIds.add(item.productId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A product may only appear once per order");
            }
            Product product = productRepository.findById(item.productId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Product ID = " + item.productId() + " has not been found"));
            if (Boolean.TRUE.equals(product.getIsDeleted()) || !Boolean.TRUE.equals(product.getIsAvailable())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Product ID = " + item.productId() + " is unavailable");
            }
            if (product.getQty() < item.qty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Insufficient stock for product ID = " + item.productId());
            }
            product.setQty(product.getQty() - item.qty());

            OrderLine line = new OrderLine();
            line.setOrder(order);
            line.setProduct(product);
            line.setQty(item.qty());
            line.setUnitPrice(product.getUnitPrice());
            order.getOrderLines().add(line);
        });

        order.setAddress(request.address());
        order.setRemark(request.remark() == null ? "" : request.remark());
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> findAll(int pageNumber, int pageSize) {
        return orderRepository.findByIsDelectedFalse(pageable(pageNumber, pageSize)).map(orderMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> findMine(String customerId, int pageNumber, int pageSize) {
        return orderRepository.findByCustomerIdAndIsDelectedFalse(customerId, pageable(pageNumber, pageSize))
                .map(orderMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(UUID id) {
        return orderMapper.toResponse(activeOrder(id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Order order = activeOrder(id);
        order.setIsDelected(true);
        orderRepository.save(order);
    }

    private Order activeOrder(UUID id) {
        return orderRepository.findById(id).filter(order -> !Boolean.TRUE.equals(order.getIsDelected()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order has not been found"));
    }

    private PageRequest pageable(int pageNumber, int pageSize) {
        if (pageNumber < 0 || pageSize < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid pagination parameters");
        }
        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "orderedAt"));
    }
}
