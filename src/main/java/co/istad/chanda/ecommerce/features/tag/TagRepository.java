package co.istad.chanda.ecommerce.features.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByName(String name);

    Optional<Tag> findByName(String name);

    List<Tag> findByNameContainsIgnoreCase(String name);
}
