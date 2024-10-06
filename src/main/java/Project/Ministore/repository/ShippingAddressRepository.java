package Project.Ministore.repository;

import Project.Ministore.Entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Integer> {

}
