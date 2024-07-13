package tlq.com.tlq_sale_management.service.ShoppingCartService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tlq.com.tlq_sale_management.dto.request.ShoppingCartRequest.AddToCartRequest;
import tlq.com.tlq_sale_management.dto.response.ShoppingCartResponse.ShoppingCartResponse;
import tlq.com.tlq_sale_management.dto.response.UserResponse;
import tlq.com.tlq_sale_management.mapper.ShoppingCartMapper;
import tlq.com.tlq_sale_management.model.Product;
import tlq.com.tlq_sale_management.model.ShoppingCart;
import tlq.com.tlq_sale_management.model.User;
import tlq.com.tlq_sale_management.repository.ProductRepository;
import tlq.com.tlq_sale_management.repository.ShoppingCartRepository;
import tlq.com.tlq_sale_management.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService{

    ShoppingCartRepository shoppingCartRepository;

    ShoppingCartMapper shoppingCartMapper;

    UserRepository userRepository;

    ProductRepository productRepository;

    @Override
    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public boolean create(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

    @Override
    public boolean update(ShoppingCart shoppingCart) {
        if (shoppingCartRepository.existsById(shoppingCart.getShoppingCartId())) {
            shoppingCartRepository.save(shoppingCart);
            return true;
        }
        return false;
    }

    @Override
    public ShoppingCart findById(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        shoppingCartRepository.delete(shoppingCart);
        return true;
    }

    public ShoppingCartResponse createCart(AddToCartRequest request) {
        if (shoppingCartRepository.existsByProductProductIdAndUserUserId(request.getProductId(), request.getUserId())) {
            ShoppingCart shoppingCart = findCartByProductIdAndUserId(request.getProductId(), request.getUserId());
            plusQuantityInCart(shoppingCart, request.getQuantity());
            System.out.println("day la createCart service");
            return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
        }

        ShoppingCart cart = new ShoppingCart();
        cart.setQuantity(request.getQuantity());
        cart.setUser(userRepository.findById(request.getUserId()).orElse(null));
        cart.setProduct(productRepository.findById(request.getProductId()).orElse(null));
        return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(cart));
    }

    private void plusQuantityInCart(ShoppingCart shoppingCart, Long quantity) {
        shoppingCart.setQuantity(shoppingCart.getQuantity() + quantity);
    }

    private void plusQuantityInCart(ShoppingCart shoppingCart) {
        shoppingCart.setQuantity(shoppingCart.getQuantity() + 1);
    }

    private void minusQuantityInCart(ShoppingCart shoppingCart) {
        shoppingCart.setQuantity(shoppingCart.getQuantity() - 1);
    }

    public ShoppingCart findCartByProductIdAndUserId(Long productId, Long userId) {
        return shoppingCartRepository.findByProductProductIdAndUserUserId(productId, userId);
    }

    public ShoppingCartResponse plusQuantityInCart(Long id) {
        ShoppingCart shoppingCart = findById(id);
        if (shoppingCart != null) {
            plusQuantityInCart(shoppingCart);
            return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
        }
        return null;
    }

    public ShoppingCartResponse minusQuantityInCart(Long id) {
        ShoppingCart shoppingCart = findById(id);
        if (shoppingCart != null) {
            minusQuantityInCart(shoppingCart);
            return shoppingCartMapper.toShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
        }
        return null;
    }


    public List<ShoppingCartResponse> getCarts(){
        log.info("In method get Carts");
        return shoppingCartRepository.findAll().stream()
                .map(shoppingCartMapper::toShoppingCartResponse).toList();
    }

    public List<ShoppingCart> getCartsByUserId(Long id) {
        return shoppingCartRepository.findByUserUserId(id);
    }


}
