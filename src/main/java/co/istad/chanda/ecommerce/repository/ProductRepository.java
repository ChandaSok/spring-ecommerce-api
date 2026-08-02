package co.istad.chanda.ecommerce.repository;

import co.istad.chanda.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends
        JpaRepository<Product, Integer> {
}
