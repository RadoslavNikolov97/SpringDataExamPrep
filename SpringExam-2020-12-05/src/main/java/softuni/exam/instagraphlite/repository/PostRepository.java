package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Posts;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {
    List<Posts> findAllByUserIdOrderByPictureSize(int id);
}
