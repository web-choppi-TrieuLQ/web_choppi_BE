package tlq.com.tlq_sale_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tlq.com.tlq_sale_management.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
