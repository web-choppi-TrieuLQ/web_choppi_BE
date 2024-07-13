package tlq.com.tlq_sale_management.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tlq.com.tlq_sale_management.model.Product;
import tlq.com.tlq_sale_management.service.ProductService.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("bat dau getAllProduct");
        List<Product> productList = productService.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<String> createProduct(@RequestParam("product") String productJson, @RequestParam("file") MultipartFile file) {
        log.error("productJson: " + productJson);
        log.error("file: "+ file.toString());
        Map uploadResult = null;
        try {
            // Chuyển đổi chuỗi JSON thành đối tượng Product
            Product product = objectMapper.readValue(productJson, Product.class);

            uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            product.setImage(imageUrl);
            productService.create(product);
            return new ResponseEntity<>("create product success", HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("lỗi exception");
            e.printStackTrace();
        }
        return new ResponseEntity<>("create fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody Product product ,@PathVariable Long id) {
        Product existingProduct = productService.findById(id);

        if (existingProduct != null) {
            modelMapper.map(product, existingProduct);
            return new ResponseEntity<>("update product success", HttpStatus.OK);
        }
        return new ResponseEntity<>("update fail", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product product = productService.findById(id);

        if (product != null) {
            productService.delete(product);
            return new ResponseEntity<>("delete product success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete fail", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
