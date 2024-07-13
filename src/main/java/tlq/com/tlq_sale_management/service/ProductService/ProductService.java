package tlq.com.tlq_sale_management.service.ProductService;

import org.springframework.web.multipart.MultipartFile;
import tlq.com.tlq_sale_management.model.Product;
import tlq.com.tlq_sale_management.service.Service;

public interface ProductService extends Service<Product> {
    boolean create(Product product, MultipartFile imageFile);
}
