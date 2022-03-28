package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Pictures;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Pictures , Integer> {
    Optional<Pictures> findByPathEquals(String profilePicture);


    @Query("SELECT p FROM Pictures As p WHERE p.size >= :sizeToGet ORDER BY p.size ASC ")
    List<Pictures> findAllBySizeGreaterThan(double sizeToGet);
}
