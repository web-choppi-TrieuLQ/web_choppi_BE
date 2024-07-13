package tlq.com.tlq_sale_management.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tlq.com.tlq_sale_management.model.Category;
import tlq.com.tlq_sale_management.service.CategoryService.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        System.out.println("bắt đầu getAllCategories");
        List<Category> categoryList = categoryService.findAll();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryService.create(category);
        return new ResponseEntity<>("create category success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        Category existingCategory = categoryService.findById(id);

        if (existingCategory != null) {
            modelMapper.map(category, existingCategory);
            return new ResponseEntity<>("update category success", HttpStatus.OK);
        }
        return new ResponseEntity<>("update fail", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        if (category != null) {
            categoryService.delete(category);
            return new ResponseEntity<>("delete category success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete fail", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}

