package tlq.com.tlq_sale_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tlq.com.tlq_sale_management.model.ShoppingCart;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long userId;
    String username;
    Set<String> roles;
    List<ShoppingCart> shoppingCarts;
}
