package co.istad.chanda.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity // JPA
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String code;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;

    @ManyToMany
    @JoinTable(
            name = "product_tag_data", // change table name
            joinColumns = @JoinColumn(name = "product_id"), // change column product
            inverseJoinColumns = @JoinColumn(name = "tag_id") // change column tag
    )
    private List<Tag> tags;

}
