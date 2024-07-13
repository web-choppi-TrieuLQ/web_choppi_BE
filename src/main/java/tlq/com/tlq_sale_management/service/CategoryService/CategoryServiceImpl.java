package tlq.com.tlq_sale_management.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tlq.com.tlq_sale_management.model.Category;
import tlq.com.tlq_sale_management.repository.CategoryRepository;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean create(Category category) {
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean update(Category category) {
        if (categoryRepository.existsById(category.getCategoryId())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Category category) {
        if (categoryRepository.existsById(category.getCategoryId())) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }
}
