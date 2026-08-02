package co.istad.chanda.ecommerce.repository;

import co.istad.chanda.ecommerce.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
