package tlq.com.tlq_sale_management.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tlq.com.tlq_sale_management.dto.request.ShoppingCartRequest.AddToCartRequest;
import tlq.com.tlq_sale_management.dto.response.SystemRespone.ApiResponse;
import tlq.com.tlq_sale_management.dto.response.ShoppingCartResponse.ShoppingCartResponse;
import tlq.com.tlq_sale_management.model.ShoppingCart;
import tlq.com.tlq_sale_management.service.ShoppingCartService.ShoppingCartServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@CrossOrigin("*")
public class ShoppingCartController {
    ShoppingCartServiceImpl shoppingCartService;

    @GetMapping("")
    public ApiResponse<List<ShoppingCartResponse>> getAllCarts() {
        return ApiResponse.<List<ShoppingCartResponse>>builder()
                .result(shoppingCartService.getCarts())
                .build();
    }

    @PostMapping("")
    public ApiResponse<ShoppingCartResponse> addToCart(@RequestBody AddToCartRequest request) {
        return ApiResponse.<ShoppingCartResponse>builder()
                .result(shoppingCartService.createCart(request))
                .build();
    }


    @GetMapping("/{cartId}")
    public ShoppingCart getCartById(@PathVariable Long cartId) {
        return shoppingCartService.findById(cartId);
    }

    @PutMapping("/plus/{cartId}")
    public ApiResponse<ShoppingCartResponse> plusQuantity(@PathVariable Long cartId) {
        return ApiResponse.<ShoppingCartResponse>builder()
                .result(shoppingCartService.plusQuantityInCart(cartId))
                .build();
    }

    @PutMapping("/minus/{cartId}")
    public ApiResponse<ShoppingCartResponse> minusQuantity(@PathVariable Long cartId) {
        return ApiResponse.<ShoppingCartResponse>builder()
                .result(shoppingCartService.minusQuantityInCart(cartId))
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteShoppingCart(@PathVariable Long cartId) {
        if (shoppingCartService.delete(shoppingCartService.findById(cartId))) {
            return new ResponseEntity<>("delete shoppingCart success",HttpStatus.OK);
        }
        return new ResponseEntity<>("delete shoppingCart fail",HttpStatus.BAD_REQUEST);
    }


}
