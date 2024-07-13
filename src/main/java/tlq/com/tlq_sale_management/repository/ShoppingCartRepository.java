package tlq.com.tlq_sale_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tlq.com.tlq_sale_management.model.ShoppingCart;

import java.util.List;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    boolean existsByProductProductIdAndUserUserId(Long productId, Long userId);

    ShoppingCart findByProductProductIdAndUserUserId(Long productId, Long userId);

    List<ShoppingCart> findByUserUserId(Long userId);
}
