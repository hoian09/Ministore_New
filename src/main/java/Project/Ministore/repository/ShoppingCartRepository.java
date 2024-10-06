package Project.Ministore.repository;

import Project.Ministore.Entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {

}
