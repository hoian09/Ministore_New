package Project.Ministore.service.impl;

import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
