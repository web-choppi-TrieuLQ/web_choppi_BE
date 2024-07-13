package tlq.com.tlq_sale_management.dto.request.ShoppingCartRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToCartRequest {
    Long userId;
    Long productId;
    Long quantity;
}
