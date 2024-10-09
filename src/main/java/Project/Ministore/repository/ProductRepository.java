package Project.Ministore.repository;

import Project.Ministore.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    // Tìm kiếm sản phẩm theo từ khóa với phân trang
    @Query("SELECT p FROM ProductEntity p WHERE p.product_name LIKE %?1%")
    Page<ProductEntity> searchProduct(String keyword, Pageable pageable);
}
