package tlq.com.tlq_sale_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tlq.com.tlq_sale_management.dto.request.ShoppingCartRequest.AddToCartRequest;
import tlq.com.tlq_sale_management.dto.response.ShoppingCartResponse.ShoppingCartResponse;
import tlq.com.tlq_sale_management.model.ShoppingCart;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {
//    @Mapping(target = "user.userId", source = "userId")
//    @Mapping(target = "product.productId", source = "productId")
//    @Mapping(target = "quantity", ignore = true)
    ShoppingCart toShoppingCart(AddToCartRequest request);

    ShoppingCartResponse toShoppingCartResponse(ShoppingCart shoppingCart);
}
