package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsernameEquals(String username);


    @Query("SELECT u FROM Users AS u JOIN Posts AS p ON p.user.id = u.id GROUP BY u.id ORDER BY count(p.id) DESC")
    List<Users> findAllOrderByPostCountAsc();
}
