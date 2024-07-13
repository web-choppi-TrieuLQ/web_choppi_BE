package tlq.com.tlq_sale_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tlq.com.tlq_sale_management.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
