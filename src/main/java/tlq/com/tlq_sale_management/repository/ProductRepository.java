package tlq.com.tlq_sale_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tlq.com.tlq_sale_management.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
