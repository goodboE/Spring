package kodong.web_ide.repository;

import kodong.web_ide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
