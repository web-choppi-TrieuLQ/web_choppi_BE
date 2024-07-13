package tlq.com.tlq_sale_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id", nullable = false)
    Long shoppingCartId;

    Long quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
