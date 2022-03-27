package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car , Integer> {

    @Query("SELECT c FROM Car AS c JOIN Picture AS p ON p.car.id = c.id GROUP BY c.id ORDER BY count(p.id) DESC , c.make DESC")
    List<Car> findAllOrderByCountByPictureIdDescAndMakeDesc();

    @Query("SELECT count(p.id) FROM Car as c join Picture as p On p.car.id = c.id group by c.id HAVING c.id = :id")
    int countByPictureIdWhereCarIdEquals(int id);
}
