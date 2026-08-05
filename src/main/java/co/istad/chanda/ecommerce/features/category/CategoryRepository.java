package co.istad.chanda.ecommerce.features.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Auto-generated query
    // Delivered query method - DQM
    // SELECT * FROM categories WHERE name = :name;
    Optional<Category> findByName(String name);


    // Select existing (SELECT * FROM categories WHERE name = :name)
    Boolean existsByName(String name);

}
