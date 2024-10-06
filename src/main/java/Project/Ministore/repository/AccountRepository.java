package Project.Ministore.repository;

import Project.Ministore.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    @Query("SELECT u FROM AccountEntity u WHERE u.username = :username")
    AccountEntity findByUsername(@Param("username") String username);
}
