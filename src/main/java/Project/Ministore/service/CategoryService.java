package Project.Ministore.service;

import Project.Ministore.Entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
public CategoryEntity saveCategory(CategoryEntity category);

    public Boolean existCategory(String name);

    public List<CategoryEntity> getAllCategory();

}
