package co.istad.chanda.ecommerce.features.tag;

import co.istad.chanda.ecommerce.features.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products;

}
