package s5almiakki.oauth2clientjwtpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s5almiakki.oauth2clientjwtpractice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
