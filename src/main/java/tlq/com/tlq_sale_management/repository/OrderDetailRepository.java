package tlq.com.tlq_sale_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tlq.com.tlq_sale_management.model.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
