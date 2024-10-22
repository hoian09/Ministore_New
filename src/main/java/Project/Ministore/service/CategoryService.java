package Project.Ministore.service;
import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {
public CategoryEntity saveCategory(CategoryEntity category);

    public Boolean existCategory(String name);

    public List<CategoryEntity> getAllCategory();

    public Page<CategoryEntity> getAllCategory(Integer pageNo);

    public List<CategoryEntity> findByActiveTrue();
}
