package Project.Ministore.repository;

import Project.Ministore.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
public CartEntity findByProductEntity_IdAndAccountEntity_Id(int productId, int accountId);
public int countByAccountEntity_Id(int accountId);
List<CartEntity> findByAccountEntity_Id(int accountId);
}
