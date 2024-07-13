package tlq.com.tlq_sale_management.dto.request.ShoppingCartRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDTO {
//    String fullName;
//    String email;
//    String phoneNumber;
//    String address;
    Long totalPayment;
    Long idAccount;
}