package tlq.com.tlq_sale_management.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tlq.com.tlq_sale_management.model.Product;
import tlq.com.tlq_sale_management.repository.ProductRepository;
import tlq.com.tlq_sale_management.service.CloudinaryService.CloudinaryService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public List<Product> findAll() {
        System.out.println("=== bat dau findAll() productService ===");
        return productRepository.findAll();
    }

    @Override
    public boolean create(Product product) {
        if (product != null) {
            productRepository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        if (productRepository.existsById(product.getProductId())) {
            productRepository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Product product) {
        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(Product product, MultipartFile imageFile) {
        if (product != null) {
            String imageUrl = cloudinaryService.uploadImage(imageFile);
            product.setImage(imageUrl);
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
