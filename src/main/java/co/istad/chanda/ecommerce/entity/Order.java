package co.istad.chanda.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private Float discount;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private String remark;

    @Column(nullable = false)
    private Boolean isDelected;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;
}
