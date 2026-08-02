package co.istad.chanda.ecommerce.repository;

import co.istad.chanda.ecommerce.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    Optional<Media> findByName(String name);
}
