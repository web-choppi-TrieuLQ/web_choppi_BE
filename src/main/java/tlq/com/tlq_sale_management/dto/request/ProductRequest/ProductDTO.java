package tlq.com.tlq_sale_management.dto.request.ProductRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    String productName;
    Double price;
    Long categoryId;
}
