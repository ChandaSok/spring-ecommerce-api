package co.istad.chanda.ecommerce.features.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Override
    @EntityGraph(attributePaths = {"orderLines", "orderLines.product"})
    Optional<Order> findById(UUID id);

    @EntityGraph(attributePaths = {"orderLines", "orderLines.product"})
    Page<Order> findByIsDelectedFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"orderLines", "orderLines.product"})
    Page<Order> findByCustomerIdAndIsDelectedFalse(String customerId, Pageable pageable);
}
