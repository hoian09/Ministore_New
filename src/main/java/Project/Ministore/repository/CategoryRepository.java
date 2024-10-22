package Project.Ministore.repository;

import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    public Boolean existsByName(String name);
    public List<CategoryEntity> findByActiveTrue();
}
