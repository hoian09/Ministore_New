package Project.Ministore.service.impl;

import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }
    @Override
    public Page<CategoryEntity> getAllCategory(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,2);// Số sản phẩm mỗi trang
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public List<CategoryEntity> findByActiveTrue() {
        List<CategoryEntity> categories = categoryRepository.findByActiveTrue();
        return categories;
    }
}
