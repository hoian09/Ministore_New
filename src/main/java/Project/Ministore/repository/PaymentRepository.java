package Project.Ministore.repository;

import Project.Ministore.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

}
