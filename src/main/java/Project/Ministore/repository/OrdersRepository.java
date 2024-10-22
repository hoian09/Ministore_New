package Project.Ministore.repository;
import Project.Ministore.Entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {
List<OrdersEntity> findByAccountEntityId(int accountId);
}
