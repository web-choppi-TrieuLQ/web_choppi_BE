package tlq.com.tlq_sale_management.ControllerTest;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tlq.com.tlq_sale_management.model.Product;
import tlq.com.tlq_sale_management.repository.ProductRepository;

import java.io.IOException;
import java.util.Map;

@Controller
public class ProductControllerTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/products")
    public String showListProduct(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "productList";
    }

    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println( "file: " + file);
        System.out.println("file name: " + file.getName());
//        System.out.println("file byte: " + file.getBytes() + "");
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");
        product.setImage(imageUrl);
        productRepository.save(product);
        System.out.println("upload thanh cong");
        return "redirect:/products";
    }
}
