package tlq.com.tlq_sale_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "username", unique = true)
    String username;

    @Column(name = "password")
    String password;

    Set<String> roles;


    @OneToMany(mappedBy = "user")
    List<OrderProduct> orders = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

    public User(Long userId) {
        this.userId = userId;
    }
}
