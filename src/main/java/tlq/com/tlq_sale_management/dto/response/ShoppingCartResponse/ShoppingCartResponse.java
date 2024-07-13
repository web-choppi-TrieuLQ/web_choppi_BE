package tlq.com.tlq_sale_management.dto.response.ShoppingCartResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tlq.com.tlq_sale_management.model.Product;
import tlq.com.tlq_sale_management.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartResponse {
    Long quantity;
//    User user;
    Product product;
}
